package prime.bd

import java.lang.Math.sqrt
import java.math.BigInteger



class primo {

    fun fuerzaBruta(num:Double):Boolean{
        return divisorPorTentativa(num)
    }
    fun raizCuadrada(num:Double):Boolean{
        return divisorPorTentativa(num,true)
    }



    private fun divisorPorTentativa(numero: Double, raizCuadrada: Boolean = false): Boolean {
        if (numero <= 1) return false  // Números <= 1 no son primos
        if (numero == 2.0) return true  // 2 es el único número primo par
        if (numero % 2 == 0.0) return false  // Números pares (excepto 2) no son primos

        val limite = if (raizCuadrada) sqrt(numero).toInt() else (numero - 1).toInt()
        val rango = (3L..limite step 2L)  // Solo impares

        for (i in rango) {
            if (numero % i == 0.0) return false
        }

        return true
    }


} // fin de clase
