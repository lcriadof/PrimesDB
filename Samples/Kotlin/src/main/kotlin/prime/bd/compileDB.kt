package prime.bd

import java.io.File
import kotlin.system.measureTimeMillis
import kotlin.math.sqrt

fun cribaDeEratostenes(limite: Int): BooleanArray {
    val primos = BooleanArray(limite + 1) { true }
    primos[0] = false
    primos[1] = false

    for (i in 2..sqrt(limite.toDouble()).toInt()) {
        if (primos[i]) {
            for (j in i * i..limite step i) {
                primos[j] = false
            }
        }
    }
    return primos
}

fun generarArchivoBinario(archivo: String, inicio: Int, final: Int) {
    if (inicio < 0 || final < inicio) {
        println("Error: Rango no válido.")
        return
    }

    val limite = maxOf(final, 11) // Ajustamos el límite mínimo a 11 para incluir el 11
    val primos = cribaDeEratostenes(limite)
    val file = File(archivo).outputStream()

    var byte = 0
    var bitPosicion = 0

    for (i in 11 until minOf(final + 1, primos.size)) {
        if (i % 2 == 0 || i % 10 == 5) continue // Descartar números pares y terminados en 5

        if (primos[i]) {
            byte = byte or (1 shl bitPosicion)
        }

        bitPosicion++
        if (bitPosicion == 8) {
            file.write(byte)
            byte = 0
            bitPosicion = 0
        }
    }

    if (bitPosicion > 0) {
        file.write(byte)
    }

    file.close()
}

fun main() {
    val rangoInicio = 10
    val rangoFinal = 1_342_177_280
    val archivo = "0000_verLCF.pdb"

    val tiempoTranscurrido = measureTimeMillis {
        generarArchivoBinario(archivo, rangoInicio, rangoFinal)
    }

    println("Archivo $archivo generado exitosamente.")
    println("Tiempo transcurrido: ${tiempoTranscurrido / 1000.0} segundos.")
}
