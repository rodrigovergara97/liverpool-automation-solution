Liverpool UI Automation Framework








Framework de automatización UI desarrollado para validar los principales flujos de compra del sitio web de Liverpool México utilizando Selenium WebDriver, Java, TestNG y Page Object Model.

El proyecto cubre navegación por categorías, selección y configuración de productos, incorporación a la bolsa y flujo de "Comprar ahora".

📌 Aplicación bajo prueba

Liverpool México

https://www.liverpool.com.mx/tienda/home

La imagen corresponde a una captura del sitio utilizada durante la ejecución de las pruebas.

🎯 Objetivo

El objetivo de la automatización es certificar los flujos críticos de comercio electrónico:

Login
   ↓
Menú de categorías
   ↓
Selección de categoría
   ↓
Filtros
   ↓
Selección de producto
   ↓
Configuración del producto
   ↓
Agregar a bolsa
   ↓
Validación del contador
   ↓
Comprar ahora

🛠️ Tecnologías
Tecnología	Versión	Uso
☕ Java	17	Lenguaje principal
🧪 Selenium	4.44.0	Automatización UI
🧪 TestNG	7.10.2	Ejecución y organización de pruebas
📦 Maven	3.8+	Gestión del proyecto
📊 ExtentReports	5.1.2	Reportes HTML
🌐 Chrome	Actual	Navegador principal
⚙️ WebDriverManager	6.3.4	Gestión del driver
📄 JSON	20250517	Configuración
🏗️ Arquitectura

El framework utiliza Page Object Model (POM) para separar las acciones de la aplicación de los casos de prueba.

                    ┌─────────────────────┐
                    │       TestNG        │
                    │     Test Cases      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Page Objects   │
                    ├─────────────────────┤
                    │ HomePage            │
                    │ LoginPage           │
                    │ CategoryPage        │
                    │ ProductPage         │
                    │ BuyNowPage          │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      BasePage       │
                    │ Common Selenium     │
                    │ functionality       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   WebDriverUtil     │
                    │ Selenium utilities  │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   Liverpool Web     │
                    └─────────────────────┘

📁 Estructura del proyecto
liverpool-automation/
│
├── pom.xml
├── testng.xml
├── README.md
│
├── docs/
│   ├── architecture.png
│   └── screenshots/
│       ├── liverpool-home.png
│       ├── categories.png
│       ├── product.png
│       └── extent-report.png
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── pages/
│   │       │   ├── BasePage.java
│   │       │   ├── HomePage.java
│   │       │   ├── LoginPage.java
│   │       │   ├── CategoryPage.java
│   │       │   ├── ProductPage.java
│   │       │   └── BuyNowPage.java
│   │       │
│   │       ├── utils/
│   │       │   ├── WebDriverUtil.java
│   │       │   └── DriverManager.java
│   │       │
│   │       └── config/
│   │           └── reporter.json
│   │
│   └── test/
│       └── java/
│           └── tests/
│               ├── BaseTest.java
│               └── ApiTest.java
│
└── target/
    └── RegressionReport.html

🧪 Casos de prueba

Los casos están organizados en tres grupos:

🟢 Positive

🔴 Negative

🟡 Edge

La prioridad se concentra en los escenarios que representan mayor riesgo para el flujo de compra.

🟢 Casos positivos
POS-001 — Login exitoso

Prioridad: P0

Objetivo: validar que un usuario pueda iniciar sesión correctamente.

Flujo:

Home
 ↓
Login
 ↓
Autenticación
 ↓
Usuario autenticado


Validación:

El header debe mostrar correctamente el estado autenticado.

@Test(
    description = "Login exitoso",
    groups = {"positive", "smoke", "regression"}
)
public void loginSuccessful() {
    // Test
}

POS-002 — Navegación por categorías

Prioridad: P0

Validar navegación hacia:

👞 Hombre → Zapatos → Botas

👞 Hombre → Zapatos → Mocasines

🌸 Belleza → Perfumes

💻 Electrónicos → Computadoras

📺 Electrónicos → Pantallas

🎮 Electrónicos → Videojuegos / Xbox

Resultado esperado:

El usuario debe llegar al listado correspondiente.

POS-003 — Agregar producto a la bolsa

Prioridad: P0

Validar:

Producto
 ↓
Características
 ↓
Agregar a bolsa
 ↓
Actualización del contador


El contador de la bolsa debe reflejar el cambio después de agregar el producto.

POS-004 — Agregar múltiples productos

Prioridad: P0

Flujo principal solicitado:

Zapatos
   ↓
Perfume
   ↓
Computadora Lenovo
   ↓
Pantalla Sony
   ↓
Xbox
   ↓
Comprar ahora


El framework valida el estado de la bolsa después de cada operación.

POS-005 — Comprar ahora

Prioridad: P0

Validar que después de seleccionar un producto se pueda utilizar:

Comprar ahora


y que el usuario sea dirigido correctamente al flujo correspondiente.

🔴 Casos negativos
NEG-001 — Login inválido

Prioridad: P1

Validar que el sistema rechace información incorrecta.

@Test(
    description = "Login con información inválida",
    groups = {"negative", "regression"}
)
public void loginWithInvalidCredentials() {
    // Test
}


Resultado esperado:

El usuario permanece en el formulario y se muestra la validación correspondiente.

NEG-002 — Agregar producto sin características

Prioridad: P0

Intentar agregar un producto sin seleccionar una característica obligatoria.

Ejemplos:

Talla

Color

Capacidad

Configuración

Resultado esperado:

El sistema debe impedir que el producto sea agregado.

NEG-003 — Producto no disponible

Prioridad: P1

Validar el comportamiento cuando un producto deja de estar disponible.

Resultado esperado:

El sistema debe impedir la compra o mostrar la información correspondiente.

🟡 Edge Cases
EDGE-001 — Incrementar cantidad

Prioridad: P1

Validar:

Cantidad = 1
      ↓
Cantidad = 2
      ↓
Cantidad = 3
      ↓
Agregar a bolsa


La cantidad final debe ser consistente con el comportamiento de la aplicación.

EDGE-002 — Agregar productos consecutivamente

Prioridad: P0

Validar que el contador de la bolsa se actualice correctamente después de varias operaciones consecutivas.

El framework evita depender de:

Thread.sleep()


y utiliza esperas explícitas:

homePage.waitForBagQuantityChange(previousQuantity);

EDGE-003 — Modal de garantía

Prioridad: P1

Algunos productos pueden mostrar un modal de garantía después de agregarlos.

El escenario valida:

Agregar producto
      ↓
Modal garantía
      ↓
No agregar garantía
      ↓
Producto permanece en bolsa

📊 Matriz de prioridades
ID	Caso	Tipo	Prioridad
POS-001	Login exitoso	Positivo	P0
POS-002	Navegación categorías	Positivo	P0
POS-003	Agregar producto	Positivo	P0
POS-004	Múltiples productos	Positivo	P0
POS-005	Comprar ahora	Positivo	P0
NEG-001	Login inválido	Negativo	P1
NEG-002	Características obligatorias	Negativo	P0
NEG-003	Producto no disponible	Negativo	P1
EDGE-001	Varias unidades	Edge	P1
EDGE-002	Productos consecutivos	Edge	P0
EDGE-003	Modal garantía	Edge	P1
⏱️ Estrategia de sincronización

Uno de los puntos importantes del framework es evitar esperas fijas innecesarias.

En lugar de:

Thread.sleep(10000);


se utilizan esperas explícitas.

Por ejemplo:

homePage.waitForBagQuantityChange(
    bagBeforeProduct
);


La validación se basa en el cambio real del estado de la aplicación.

Esto resulta especialmente importante para el contador de la bolsa, ya que la actualización puede ser asíncrona.

🛒 Validación de la bolsa

El framework obtiene el contador utilizando el atributo data-testid del componente del header.

private final By bagQuantity =
    By.cssSelector(
        "div[data-testid$='-header-shopping-cart-header-cart-quantity']"
    );


La validación se realiza mediante:

int previousQuantity =
    homePage.getCurrentBagQuantity();

productPage.addProductToBag();

homePage.waitForBagQuantityChange(
    previousQuantity
);

int currentQuantity =
    homePage.getCurrentBagQuantity();


Esto evita asumir que el contador representa necesariamente el total de unidades.

🔄 Manejo de productos dinámicos

Los listados de productos pueden cambiar entre ejecuciones.

Por esta razón, el framework utiliza selectores basados principalmente en:

data-testid


en lugar de depender exclusivamente de clases CSS generadas dinámicamente.

Ejemplo:

button[data-testid$='add-to-bag-button']

🧩 Manejo de errores

El framework contempla situaciones comunes de Selenium:

TimeoutException
NoSuchElementException
StaleElementReferenceException
NumberFormatException


También existen reintentos controlados para elementos cuyo DOM puede cambiar durante la interacción.

📈 ExtentReports

Las ejecuciones generan un reporte HTML mediante ExtentReports.

Ubicación:

target/RegressionReport.html


Ejemplo de evidencia:

El reporte permite revisar:

Estado de las pruebas.

Duración.

Ambiente.

Sistema operativo.

Versión de Java.

Resultados de ejecución.

▶️ Ejecución
Ejecutar la suite completa
mvn clean test

Ejecutar Chrome
mvn clean test -Dbrowser=chrome

Ejecutar en headless
mvn clean test -Dheadless=true

Ejecutar ambiente QA
mvn clean test -Denvironment=qa

Ejecutar combinando parámetros
mvn clean test -Dbrowser=chrome -Denvironment=qa -Dheadless=true

🧪 Grupos TestNG

Los casos pueden ejecutarse por grupos.

Smoke
<groups>
    <run>
        <include name="smoke"/>
    </run>
</groups>

Positivos
<groups>
    <run>
        <include name="positive"/>
    </run>
</groups>

Negativos
<groups>
    <run>
        <include name="negative"/>
    </run>
</groups>

Edge
<groups>
    <run>
        <include name="edge"/>
    </run>
</groups>

Regresión
<groups>
    <run>
        <include name="regression"/>
    </run>
</groups>

⚠️ Riesgos identificados
Productos dinámicos

Los productos pueden cambiar de posición, precio o disponibilidad.

Por este motivo, seleccionar productos únicamente por índice puede provocar inestabilidad.

Cambios en la UI

Cambios en:

data-testid
HTML
textos
categorías
filtros


pueden requerir modificaciones en los Page Objects.

Dependencia del ambiente

Las pruebas dependen de la disponibilidad del sitio y de sus servicios externos.

Autenticación

El proceso de autenticación puede incluir mecanismos adicionales que afecten la automatización.

Datos variables

Los filtros y resultados de búsqueda pueden variar durante diferentes ejecuciones.

🚀 Mejoras futuras

📸 Capturas automáticas cuando una prueba falla.

📹 Video automático de ejecución.

🔎 Selección de productos por nombre en lugar de índice.

📊 DataProviders de TestNG.

⚡ Ejecución paralela.

🌐 Ejecución cross-browser.

🔄 Integración CI/CD.

📦 Generación automática de artefactos.

📈 Integración de reportes con pipeline.

🧪 Mayor cobertura de escenarios negativos.

📋 Flujo certificado

El flujo principal cubierto por la automatización es:

                    LOGIN
                      │
                      ▼
              CATEGORÍAS
                      │
          ┌───────────┼───────────┐
          ▼           ▼           ▼
       ZAPATOS      PERFUMES   ELECTRÓNICOS
          │                       │
      ┌───┴───┐             ┌─────┼─────┐
      ▼       ▼             ▼     ▼     ▼
    BOTAS  MOCASINES      LENOVO  SONY  XBOX
      │       │             │     │     │
      └───────┴─────────────┴─────┴─────┘
                      │
                      ▼
                AGREGAR A BOLSA
                      │
                      ▼
               VALIDAR BOLSA
                      │
                      ▼
                 COMPRAR AHORA

📦 Entregables

El proyecto contempla los siguientes entregables:

Código fuente del framework.

Page Objects.

Casos de prueba TestNG.

testng.xml.

pom.xml.

Configuración de ExtentReports.

README con documentación.

Reporte de ejecución.

Evidencias de ejecución.

Video explicativo de la solución.

📌 Resultado esperado

La solución debe permitir ejecutar de forma automatizada los escenarios críticos del flujo de compra de Liverpool:

Login
  ↓
Navegación
  ↓
Selección
  ↓
Configuración
  ↓
Agregar a bolsa
  ↓
Validar bolsa
  ↓
Comprar ahora


La automatización se enfoca en los escenarios de mayor prioridad para reducir el riesgo sobre las funcionalidades principales del proceso de compra.
