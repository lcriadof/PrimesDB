Versión kotlin
------------------
- compilador kotlin: 2.0.21
- máquina virtual java (jvm): 18 

### checkPrime.kt:
- ubicación: src/main/kotlin/prime/bd
- propósito: comprueba si el número introducido es primo (gemelo a checkPrime.py)
   

### chekPrime.ipynb:
- ubicación: notebooks
- propósito: kotlin-jupyter-notebook gemelo a checkPrime.kt


### docker chekPrime.ipynb:
Este Docker está diseñado para que cualquier usuario, incluso sin experiencia en Kotlin, pueda verificar si un número es primo utilizando un Jupyter Notebook con soporte para Kotlin.

- docker instalación:<br> docker pull lcriadof/jupyter:kotlin.v4<br><br>
- docker ejecución: <br>docker run --name jupyter-kotlin -p 8888:8888 lcriadof/jupyter:kotlin.v3<br><br>
- docker [acceso](http://localhost:8888)<br><br>

FAQ: Una vez en ejecución ¿cómo probar si un número es primo?
Abre el navegador y ve a:
📌 http://localhost:8888/lab/tree/work/checkPrime.ipynb
