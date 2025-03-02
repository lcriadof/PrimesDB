
### checkPrime.kt:

  programa kotlin que comprueba si el número introducido es primo (gemelo a checkPrime.py)
   
  Explicación de <b>esPrimoBaseDatos()</b>, función que realiza el trabajo y que invoca main() 

- 1: Se trata de los primeros primos conocidos, si se detectan se sale de la función devolviendo 1
- 2: Obtenemos la terminación del número
- 3: Los números terminados en 0, 2, 4, 5, 6, 8 no pueden ser primos, si se detectan se sale de la función devolviendo 0
- 4: Calculamos la dirección del byte donde se encuentra la década en la base de datos.
Por ejemplo, si queremos saber si el número 756101 es primo, lo primero identificamos la década con “val decada = numero / 10” y obtendremos 75610
Cada byte de la base de datos almacena la información de dos décadas. Por lo tanto, la dirección del byte dentro del archivo se obtiene dividiendo la década entre 2, sumando 0.5 y luego truncando, al resultado de esto le restamos 1.
La corrección de sumar 0.5 antes de truncar asegura localizar la década en el nibble correcto. En nuestro ejemplo, dirección= truncado (75610/2 + 0,5) - 1 => dirección = truncado (37805,5) – 1= 37804
Por lo tanto, el byte 37804 de la base de datos es el que contiene la información sobre el número 756101. Es decir, la dirección del byte en hexadecimal se encuentra en la posición 0x93AC.
- 6: Determinamos la posición del bit dentro del nibble adecuado, basándonos en el byte seleccionado. Para ello tomamos el último dígito del número, en nuestro ejemplo, del número 756101
[6.1] Definimos un mapa (mapOf) del nibble que asocia dígitos finales posibles (específicamente 1, 3, 7 y 9) a posiciones específicas de bits dentro de un byte:
1 to 0: El último dígito es 1, y se asigna a la posición de bit 0.
3 to 1: El último dígito es 3, y se asigna a la posición de bit 1.
7 to 2: El último dígito es 7, y se asigna a la posición de bit 2.
9 to 3: El último dígito es 9, y se asigna a la posición de bit 3.
[6.2] Obtenemos la posición del bit en base al mapa anterior, si no se encuentra una terminación mapeada se sale de la función devolviendo -1
[6.3] Seleccionamos el nibble adecuado. Si la década es par, el bit está en la parte alta del byte (nibble alto) en caso contrario está en el nibble bajo
- 7: Extraemos el nibble correspondiente
- 8: Obtenemos el bit dentro del nibble. Lo encontramos en el bit 4: al contener un 1 indica que es primo

