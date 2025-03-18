/*
Programa que comprueba si el número introducido es primo.
 El valor máximo comprobable es 1.342.177.280
 El valor máximo es el del archivo binario usado como tabla de verificación '0000.pdb'.
 El resultado es instantáneo.
 Se han seguido las pautas especificadas por ChuxMan en el siguiente repositorio:
  https://github.com/pekesoft/PrimesDB

 by @lcriadof
 XX-02-2025
*/
package prime.bd
import java.io.File
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.config.Configurator
import java.nio.file.Paths

// explicación de la función esPrimoBaseDatos() en checkPrime.md (mismo directorio)
fun esPrimoBaseDatos(numero: Int, baseDatos: ByteArray): Int {
    if (numero in listOf(1, 2, 3, 5, 7)) return 1 // 1
    val ultimoDigito = numero % 10 // 2
    if (ultimoDigito in listOf(0, 2, 4, 5, 6, 8)) return 0 // 3
    val decada = numero / 10 // [4] dirección del byte
    val direccion = (decada / 2.0 + 0.5).toInt() - 1  // fin de 4

    // [6] Determinamos la posición del bit dentro del nibble en base al byte seleccionado
    val bits = mapOf(1 to 0, 3 to 1, 7 to 2, 9 to 3) // 6.1
    val posicionBit = bits[ultimoDigito] ?: return -1 // 6.2
    val bitPosicionFinal = if (decada % 2 == 0) posicionBit + 4 else posicionBit // 6.3
    // fin de 6

    // Extraer el nibble correspondiente
    val byte = baseDatos[direccion].toInt() and 0xFF // 7
    val bitExtraido = (byte shr bitPosicionFinal) and 1  // 8
    return bitExtraido
}



fun main() {

    var archivo = java.io.File("../../PrimesDB/0000.pdb")

    if (!archivo.exists()) {
        println("No se encontró la base de datos de primos.")
        return
    }

    val baseDatos = archivo.readBytes()

    //val numero = 756101
    val numero = 11
    if (numero > 1_342_177_280) {
        println("Introduzca un número válido menor o igual a 1342177280")
    }else{
        val resultado = esPrimoBaseDatos(numero, baseDatos)
        println("$numero ${if (resultado==1) "ES PRIMO" else "no es primo"}")
    }
}

