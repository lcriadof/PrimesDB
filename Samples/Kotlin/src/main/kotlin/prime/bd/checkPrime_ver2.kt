package prime.bd

fun esPrimoBaseDatos(numero: Long, baseDatos: ByteArray, inicioRango: Long): Int {
    if (numero in listOf(1L, 2L, 3L, 5L, 7L)) return 1 // 1
    val ultimoDigito = (numero % 10).toInt() // 2
    if (ultimoDigito in listOf(0, 2, 4, 5, 6, 8)) return 0 // 3

    val numeroAjustado = (numero - inicioRango).toInt() // Ajustamos al inicio del archivo
    if (numeroAjustado < 0) return -1  // Previene índices negativos

    val decada = numeroAjustado / 10 // [4] dirección del byte
    val direccion = (decada / 2.0).toInt()  // Corregido para evitar -1

    if (direccion < 0 || direccion >= baseDatos.size) return -1 // Verificación extra

    val bits = mapOf(1 to 0, 3 to 1, 7 to 2, 9 to 3) // 6.1
    val posicionBit = bits[ultimoDigito] ?: return -1 // 6.2
    val bitPosicionFinal = if (decada % 2 == 0) posicionBit + 4 else posicionBit // 6.3

    val byte = baseDatos[direccion].toInt() and 0xFF // 7
    val bitExtraido = (byte shr bitPosicionFinal) and 1  // 8
    return bitExtraido
}


fun main() {
    //val url:String="./PrimesDB/"
    val url:String="../../PrimesDB/"

    val archivo0 = java.io.File(url+"0000.pdb")
    val archivo1 = java.io.File(url+"0001.pdb")
    val archivo2 = java.io.File(url+"0002.pdb")

    if (!archivo0.exists() || !archivo1.exists() || !archivo2.exists()) {
        println("No se encontró una o varias bases de datos de primos.")
        return
    }

    val baseDatos0 = archivo0.readBytes()
    val baseDatos1 = archivo1.readBytes()
    val baseDatos2 = archivo2.readBytes()

    val numero = 2_684_354_599L // numero para probar

    val resultado = when {
        numero in 11L..1_342_177_289L -> esPrimoBaseDatos(numero, baseDatos0, 11L)
        numero in 1_342_177_291L..2_684_354_569L -> esPrimoBaseDatos(numero, baseDatos1, 1_342_177_291L)
        numero in 2_684_354_571L..4_026_531_849L -> esPrimoBaseDatos(numero, baseDatos2, 2_684_354_571L)
        else -> {
            println("Número fuera de rango. Introduzca un número entre 11 y 4.026.531.849")
            return
        }
    }

    println("$numero ${if (resultado == 1) "ES PRIMO" else "no es primo"}")
}

