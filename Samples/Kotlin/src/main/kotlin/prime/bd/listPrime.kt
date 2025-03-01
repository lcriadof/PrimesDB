// lista un rango de numeros primos
package prime.bd
import java.io.File

fun obtenerPrimosEnRango(baseDatos: ByteArray, inicioByte: Int, finByte: Int): List<Int> {
    val primosEncontrados = mutableListOf<Int>()
    val bits = listOf(1, 3, 7, 9) // Últimos dígitos de números impares posibles

    for (direccion in inicioByte until finByte.coerceAtMost(baseDatos.size)) {
        val byte = baseDatos[direccion].toInt() and 0xFF // Convertir a entero sin signo
        val decada = (direccion + 1) * 2 * 10 // Convertir dirección de byte a década base

        for (i in 0..7) {
            val esPrimo = (byte shr i) and 1 == 1
            if (esPrimo) {
                val esDecadaPar = i >= 4
                val posicionEnNibble = i % 4
                val ultimoDigito = bits[posicionEnNibble]

                val numeroPrimo = (if (esDecadaPar) decada else decada - 10) + ultimoDigito
                primosEncontrados.add(numeroPrimo)
            }
        }
    }

    return primosEncontrados
}

fun main() {
    val archivo = File("../../PrimesDB/0000.pdb")
    if (!archivo.exists()) {
        println("No se encontró la base de datos de primos.")
        return
    }

    val baseDatos = archivo.readBytes()
    val inicioByte = 37804
    val finByte = 37806

    val primos = obtenerPrimosEnRango(baseDatos, inicioByte, finByte)

    println("Números primos encontrados en el rango de bytes $inicioByte a $finByte:")
    println(primos.joinToString(", "))
}
