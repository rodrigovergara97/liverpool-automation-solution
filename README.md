
 # Liverpool UI Automation Framework

 Framework de automatización UI desarrollado para validar flujos críticos de compra del sitio web de **Liverpool México**, utilizando **Java, Selenium WebDriver, TestNG, Maven, Page Object Model y ExtentReports**.

 La automatización se enfoca en los escenarios funcionales y de regresión de mayor prioridad relacionados con:

 - Autenticación.
- Navegación por categorías.
- Selección y configuración de productos.
- Agregado de productos a la bolsa.
- Validación del contador de la bolsa.
- Compra de productos.
- Validaciones negativas.
- Validaciones de cantidades máximas.
- Persistencia de información durante un refresh.

---

 ## Aplicación bajo prueba

 **Liverpool México**

 https://www.liverpool.com.mx/tienda/home

 La automatización utiliza el sitio de Liverpool México como aplicación bajo prueba.

---

 ## Objetivo

 El objetivo del framework es validar los principales escenarios críticos del flujo de compra mediante pruebas automatizadas de UI.

 El flujo general es:

```
                    ┌──────────────┐
                    │    LOGIN     │
                    └──────┬───────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │   CATEGORÍAS    │
                  └────────┬────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ FILTROS /       │
                  │ PRODUCTOS       │
                  └────────┬────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ CONFIGURACIÓN   │
                  │ DEL PRODUCTO    │
                  └────────┬────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ AGREGAR A BOLSA │
                  └────────┬────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ VALIDAR BOLSA   │
                  └────────┬────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │   COMPRAR AHORA │
                  └─────────────────┘
```

---

 ## Tecnologías

 | Tecnología | Versión | Uso |
| --- | --- | --- |
| Java | 17 | Lenguaje principal |
| Selenium WebDriver | 4.44.0 | Automatización UI |
| TestNG | 7.10.2 | Ejecución y organización de pruebas |
| Maven | 3.8+ | Gestión y ejecución del proyecto |
| ExtentReports | 5.1.2 | Reportes HTML |
| WebDriverManager | 6.3.4 | Gestión de WebDriver |
| JSON | 20250517 | Configuración |
| Chrome | Actual | Navegador principal |

---

 ## Arquitectura

 El framework utiliza **Page Object Model (POM)** para separar la lógica de interacción con la aplicación de los casos de prueba.

```
                           ┌─────────────────────┐
                           │       TestNG        │
                           │     Test Cases      │
                           └──────────┬──────────┘
                                      │
                                      ▼
                           ┌─────────────────────┐
                           │    PurchaseTest     │
                           └──────────┬──────────┘
                                      │
                                      ▼
             ┌───────────────────────────────────────────┐
             │                Page Objects                │
             ├───────────────────────────────────────────┤
             │ HomePage                                  │
             │ LoginPage                                 │
             │ CategoryPage                              │
             │ ProductPage                               │
             │ BuyNowPage                                │
             └──────────────────────┬────────────────────┘
                                    │
                                    ▼
                           ┌─────────────────────┐
                           │      BasePage       │
                           │ Selenium utilities  │
                           └──────────┬──────────┘
                                      │
                                      ▼
                           ┌─────────────────────┐
                           │    DriverManager    │
                           │    WebDriver        │
                           └──────────┬──────────┘
                                      │
                                      ▼
                           ┌─────────────────────┐
                           │ Liverpool México    │
                           └─────────────────────┘
```

---

 # Casos automatizados

 Los casos implementados actualmente en `PurchaseTest` se dividen en:

 - **Functional / Regression**
- **Negative / Regression**
- **Edge / Regression**

 La cobertura se concentra en los escenarios de mayor prioridad del flujo de compra.

---

 # 1\. Login exitoso

 ### ID

 `TC-001`

 ### Tipo

 Functional / Regression

 ### Prioridad

 P0

 ### Descripción

 Valida que un usuario pueda iniciar sesión correctamente.

 ### Flujo

```
┌──────────────┐
│ Home Liverpool│
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Login        │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Autenticación│
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Usuario      │
│ autenticado  │
└──────────────┘
```

 ### Validación

 Se valida que el usuario haya iniciado sesión correctamente.

```
Assert.assertTrue(
    homePage.isLoginSuccessful(),
    "Login was not successful"
);
```

 ### TestNG

```
@Test(
    description = "login exitoso",
    groups = {"functional", "regression"},
    testName = "login exitoso"
)
public void login() {
    // Test
}
```

---

 # 2\. Flujo E2E positivo — múltiples productos

 ### ID

 `TC-002`

 ### Tipo

 Functional / Regression

 ### Prioridad

 P0

 Este es el flujo principal de compra automatizado.

 El escenario navega por diferentes categorías, aplica filtros, configura productos, agrega productos a la bolsa y finalmente ejecuta `Comprar ahora`.

 ### Flujo general

```
                         LOGIN
                           │
                           ▼
                     CATEGORÍAS
                           │
            ┌──────────────┼──────────────┐
            │              │              │
            ▼              ▼              ▼
         HOMBRE          BELLEZA      ELECTRÓNICOS
            │              │              │
            ▼              ▼              ▼
         ZAPATOS        PERFUMES      PANTALLAS
            │              │              │
            ▼              ▼              ▼
       MOCASINES       PERFUMES       SONY
                         HOMBRE
            │
            │
            └──────────────────────┐
                                   │
                                   ▼
                              COMPUTADORAS
                                   │
                                   ▼
                                 LENOVO
                                   │
                                   ▼
                            CONFIGURACIÓN
                                   │
                                   ▼
                            AGREGAR BOLSA
                                   │
                                   ▼
                          VALIDAR CONTADOR
                                   │
                                   ▼
                            COMPRAR AHORA
```

 ### Productos involucrados

 El flujo utiliza diferentes categorías para validar distintos comportamientos:

```
Hombre
 └── Zapatos
      ├── Mocasines
      └── Botas

Belleza
 └── Perfumes
      └── Perfumes Hombre

Electrónicos
 ├── Pantallas
 │    └── Sony
 │
 └── Computadoras
      └── Lenovo
```

 ### Validación de la bolsa

 Antes de agregar cada producto se obtiene la cantidad actual:

```
int bagBeforeProduct =
        homePage.getCurrentBagQuantity();
```

 Después de agregarlo se espera el cambio del contador:

```
homePage.waitForBagQuantityChange(
        bagBeforeProduct
);
```

 Finalmente se valida que el contador haya aumentado:

```
int bagAfterProduct =
        homePage.getCurrentBagQuantity();

Assert.assertTrue(
        bagAfterProduct > bagBeforeProduct,
        "Product was not reflected in the shopping bag"
);
```

 ### Ventaja

 La prueba no asume que la actualización del carrito sea inmediata. La validación espera el cambio real del estado de la aplicación.

---

 # 3\. Agregar producto sin sesión activa

 ### ID

 `TC-003`

 ### Tipo

 Negative / Regression

 ### Prioridad

 P1

 ### Descripción

 Valida el comportamiento cuando un usuario intenta continuar con la compra sin tener una sesión activa.

 ### Flujo

```
┌──────────────┐
│ Home         │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Categoría    │
│ Hombre       │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Zapatos      │
│ Mocasines    │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Producto     │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Comprar ahora│
└──────┬───────┘
       │
       ▼
┌──────────────────┐
│ Formulario Login │
└──────────────────┘
```

 ### Validación

 Se espera que el formulario de autenticación sea mostrado:

```
Assert.assertTrue(
    loginPage.isFormDisplayed(),
    "Login form is not displayed"
);
```

---

 # 4\. Producto sin características obligatorias

 ### ID

 `TC-004`

 ### Tipo

 Negative / Regression

 ### Prioridad

 P0

 ### Descripción

 Valida que un producto que requiere información obligatoria no pueda agregarse a la bolsa mientras dicha información no haya sido seleccionada.

 Ejemplos de información obligatoria:

```
Producto
   │
   ├── Talla
   ├── Color
   ├── Capacidad
   └── Configuración
```

 ### Flujo

```
┌─────────────────┐
│ Seleccionar     │
│ producto        │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Agregar a bolsa │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ ¿Faltan datos?  │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Mostrar error   │
└─────────────────┘
```

 ### Validación

```
productPage.addProductToBag(false);

Assert.assertTrue(
    productPage.isErrorMessageDisplayed(),
    "Error message is not being displayed"
);
```

 El caso valida que la aplicación impida continuar cuando existen características obligatorias sin seleccionar.

---

 # 5\. Comprar ahora después de refresh

 ### ID

 `TC-005`

 ### Tipo

 Edge / Regression

 ### Prioridad

 P1

 ### Descripción

 Valida que la información del producto seleccionado para `Comprar ahora` permanezca consistente después de actualizar la página.

 ### Flujo

```
┌──────────────┐
│ Seleccionar  │
│ producto     │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Comprar ahora│
└──────┬───────┘
       │
       ▼
┌─────────────────────┐
│ Capturar información│
│ precio / cantidad   │
└──────────┬──────────┘
           │
           ▼
      REFRESH PAGE
           │
           ▼
┌─────────────────────┐
│ Validar nuevamente  │
│ precio / cantidad   │
└─────────────────────┘
```

 ### Datos validados

 Antes del refresh:

```
double originalItemPrice =
        buyNowPage.getOriginalItemPrice();

int currentProductQuantity =
        buyNowPage.getCurrentProductQuantity();
```

 Después:

```
driver.navigate().refresh();
```

 Se comparan nuevamente los valores:

```
Assert.assertEquals(
    originalItemPrice,
    buyNowPage.getOriginalItemPrice(),
    "Product's price doesn't remain the same after refresh"
);

Assert.assertEquals(
    currentProductQuantity,
    buyNowPage.getCurrentProductQuantity(),
    "Product's quantity doesn't remain the same after refresh"
);
```

---

 # 6\. Cantidad superior a la disponible

 ### ID

 `TC-006`

 ### Tipo

 Edge / Regression

 ### Prioridad

 P1

 ### Descripción

 Valida el comportamiento de la aplicación cuando se intenta agregar una cantidad superior a la disponibilidad permitida del producto.

 En la prueba se establece:

```
productPage.setProductQuantity(100);
```

 Posteriormente se intenta agregar el producto:

```
productPage.addProductToBag(false);
```

 ### Flujo

```
┌──────────────────────┐
│ Seleccionar producto │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Solicitar cantidad   │
│ 100 unidades         │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Agregar a bolsa      │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Validar resultado    │
└──────────┬───────────┘
           │
           ▼
     No debe existir
     agregado exitoso
```

 ### Validación

```
Assert.assertFalse(
    productPage.isSuccessMessageDisplayed()
);
```

---

 # Matriz de pruebas

 | ID | Caso | Tipo | Prioridad | Grupo |
| --- | --- | --- | --- | --- |
| TC-001 | Login exitoso | Functional | P0 | functional / regression |
| TC-002 | Flujo E2E múltiples productos | Functional | P0 | functional / regression |
| TC-003 | Compra sin sesión activa | Negative | P1 | negative / regression |
| TC-004 | Producto sin características obligatorias | Negative | P0 | negative / regression |
| TC-005 | Comprar ahora después de refresh | Edge | P1 | edge / regression |
| TC-006 | Cantidad superior a disponibilidad | Edge | P1 | edge / regression |

---

 # Sincronización

 El framework evita depender de esperas fijas como:

```
Thread.sleep(10000);
```

 En su lugar utiliza esperas explícitas basadas en el estado de la aplicación.

 Por ejemplo:

```
int previousQuantity =
        homePage.getCurrentBagQuantity();

productPage.addProductToBag(true);

homePage.waitForBagQuantityChange(
        previousQuantity
);
```

 Esto permite sincronizar la prueba con el comportamiento real de la aplicación.

---

 # Validación del contador de la bolsa

 El contador se obtiene desde el elemento del header.

 El framework utiliza un selector basado en `data-testid`:

```
private final By bagQuantity =
    By.cssSelector(
        "div[data-testid$='-header-shopping-cart-header-cart-quantity']"
    );
```

 La estrategia utilizada es:

```
Cantidad anterior
       │
       ▼
Agregar producto
       │
       ▼
Esperar actualización
       │
       ▼
Obtener cantidad nueva
       │
       ▼
Comparar
```

 Esto evita asumir que la actualización del carrito ocurre inmediatamente.

---

 # Manejo de productos dinámicos

 Los resultados de productos pueden cambiar entre ejecuciones.

 Por esta razón, el framework utiliza principalmente atributos estables como:

```
data-testid
```

 Ejemplo:

```
button[data-testid$='add-to-bag-button']
```

 Sin embargo, actualmente algunos escenarios utilizan la posición del producto:

```
categoryPage.selectItemByNumberInPage(4);
```

 Esto representa un punto de atención para la estabilidad de las pruebas.

---

 # ExtentReports

 El framework utiliza **ExtentReports 5.1.2** para generar reportes HTML.

 El reporte se genera en:

```
target/ExtentReports/RegressionReport.html
```

 Después de ejecutar:

```
mvn clean test
```

 la estructura esperada es:

```
target/
└── ExtentReports/
    └── RegressionReport.html
```

 El reporte contiene información de la ejecución y datos del ambiente.

 Entre ellos:

```
OS
OS Version
Java
Browser
Environment
Headless
```

 La configuración visual del reporte se mantiene en:

```
src/main/java/config/reporter.json
```

 Configuración utilizada:

```
{
  "theme": "DARK",
  "encoding": "utf-8",
  "protocol": "HTTPS",
  "timelineEnabled": true,
  "offlineMode": false,
  "documentTitle": "Reporte de Regresion",
  "reportName": "Liverpool Regression Suite",
  "timeStampFormat": "MMM dd, yyyy HH:mm:ss a"
}
```

---

 # Estructura de reportes

 Durante la ejecución Maven también genera los resultados propios de Surefire:

```
target/
├── ExtentReports/
│   └── RegressionReport.html
│
└── surefire-reports/
    ├── *.xml
    ├── *.txt
    └── otros archivos de ejecución
```

 `ExtentReports` proporciona la visualización HTML de la ejecución, mientras que `Surefire` contiene los resultados utilizados por Maven/TestNG.

---

 # Configuración de ejecución

 Los parámetros principales se encuentran configurados en `pom.xml`.

 Ejemplo:

```
<browser>chrome</browser>
<environment>qa</environment>
<headless>false</headless>
```

 Estos valores pueden sobrescribirse desde Maven.

---

 # Requisitos

 Antes de ejecutar el proyecto se requiere:

 - Java 17 o superior.
- Maven 3.8 o superior.
- Google Chrome instalado.
- Acceso a Internet.
- Acceso al ambiente de Liverpool utilizado para las pruebas.
- Proyecto correctamente configurado en IntelliJ IDEA, Eclipse o IDE equivalente.

 Validar Java:

```
java -version
```

 Validar Maven:

```
mvn -version
```

---

 # Setup del proyecto

 Clonar el repositorio y acceder al proyecto:

```
git clone <repository-url>
cd liverpool-automation
```

 Verificar que Maven pueda resolver las dependencias:

```
mvn clean
```

 Compilar el proyecto:

```
mvn compile
```

 Ejecutar las pruebas:

```
mvn test
```

---

 # Ejecución con Maven

 ## Suite completa

```
mvn clean test
```

 ## Chrome

```
mvn clean test -Dbrowser=chrome
```

 ## Headless

```
mvn clean test -Dheadless=true
```

 ## Ambiente QA

```
mvn clean test -Denvironment=qa
```

 ## Combinando parámetros

```
mvn clean test -Dbrowser=chrome -Denvironment=qa -Dheadless=true
```

---

 # Ejecución por grupos TestNG

 Los casos utilizan los siguientes grupos:

```
functional
negative
edge
regression
```

 ## Functional

```
<groups>
    <run>
        <include name="functional"/>
    </run>
</groups>
```

 ## Negative

```
<groups>
    <run>
        <include name="negative"/>
    </run>
</groups>
```

 ## Edge

```
<groups>
    <run>
        <include name="edge"/>
    </run>
</groups>
```

 ## Regression

```
<groups>
    <run>
        <include name="regression"/>
    </run>
</groups>
```

---

 # Setup de TestNG

 La clase `PurchaseTest` inicializa el driver y los Page Objects:

```
PurchaseTest
     │
     ▼
setUp()
     │
     ├── DriverManager
     │
     ├── HomePage
     │
     ├── LoginPage
     │
     ├── CategoryPage
     │
     ├── ProductPage
     │
     └── BuyNowPage
```

 El navegador utilizado actualmente es Chrome:

```
DriverManager.setDriver("chrome");
```

---

 # Dependencias Maven

 Las principales dependencias utilizadas son:

```
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.44.0</version>
</dependency>
```

```
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.10.2</version>
    <scope>test</scope>
</dependency>
```

```
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.1.2</version>
</dependency>
```

```
<dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>6.3.4</version>
</dependency>
```

---

 # Manejo de errores

 El framework contempla problemas comunes durante la automatización UI, incluyendo:

```
TimeoutException
NoSuchElementException
StaleElementReferenceException
NumberFormatException
```

 También se utilizan mecanismos de reintento controlados para elementos cuyo DOM puede cambiar durante la ejecución.

---

 # Riesgos y defectos identificados

 ## Productos dinámicos

 Los productos disponibles pueden cambiar entre ejecuciones.

 Esto puede afectar:

 - Posición del producto.
- Precio.
- Disponibilidad.
- Resultados del listado.
- Cantidad disponible.

 Actualmente algunos casos seleccionan productos mediante posición:

```
selectItemByNumberInPage(4);
```

 Por lo tanto, un cambio en el orden del listado puede provocar una falla aunque la funcionalidad continúe operando correctamente.

---

 ## Filtros de talla y color

 Existe un riesgo funcional importante en la experiencia de selección de productos.

 Aunque el usuario aplique filtros de **talla o color desde el listado de productos**, al entrar posteriormente al detalle del producto, algunos zapatos pueden requerir nuevamente la selección de talla o color.

 El comportamiento puede representarse así:

```
Listado de productos
        │
        ▼
Filtro: Color
        │
        ▼
Filtro: Talla
        │
        ▼
Producto filtrado
        │
        ▼
Detalle del producto
        │
        ▼
¿Talla / color seleccionado?
        │
        └────── NO ──────► Solicitar nuevamente selección
```

 ### Riesgo / defecto

 El filtro aplicado en el listado no necesariamente se conserva como selección de la variante del producto.

 Esto puede generar:

 - Confusión para el usuario.
- Selección repetida de atributos.
- Intentos de agregar productos sin completar características obligatorias.
- Errores al utilizar `Agregar a bolsa`.
- Diferencia entre el producto filtrado y la variante realmente seleccionada.

 Este comportamiento debe considerarse un **riesgo funcional y potencial defecto de UX**, especialmente en productos como zapatos donde talla y color son atributos relevantes para la compra.

---

 ## Cambios en la UI

 Cambios en elementos como:

```
data-testid
HTML
textos
categorías
filtros
estructura del DOM
```

 pueden requerir modificaciones en los Page Objects.

---

 ## Dependencia del ambiente

 Las pruebas dependen de la disponibilidad del sitio web y de servicios externos.

 Una falla de red, disponibilidad del ambiente o servicio externo puede provocar una falla de automatización que no necesariamente representa un defecto funcional.

---

 ## Datos variables

 Los resultados de categorías, filtros y productos pueden variar entre ejecuciones.

 Por este motivo, los escenarios que dependen de productos específicos pueden requerir mantenimiento.

---

 ## Disponibilidad de productos

 Un producto utilizado durante una prueba puede:

 - Agotarse.
- Cambiar de precio.
- Cambiar de categoría.
- Cambiar de disponibilidad.
- Dejar de aparecer en los resultados esperados.

 Esto puede afectar la estabilidad de la prueba.

---

 # Flujo E2E principal

 El flujo funcional principal implementado actualmente puede representarse de la siguiente manera:

```
                         ┌──────────────┐
                         │    LOGIN     │
                         └──────┬───────┘
                                │
                                ▼
                       ┌──────────────────┐
                       │    CATEGORÍAS    │
                       └────────┬─────────┘
                                │
             ┌──────────────────┼───────────────────┐
             │                  │                   │
             ▼                  ▼                   ▼
        ┌─────────┐        ┌─────────┐        ┌────────────┐
        │ HOMBRE  │        │ BELLEZA │        │ELECTRÓNICA │
        └────┬────┘        └────┬────┘        └─────┬──────┘
             │                  │                   │
             ▼                  ▼                   ├────────────┐
        ┌─────────┐        ┌─────────┐              │            │
        │ ZAPATOS │        │ PERFUMES│              ▼            ▼
        └────┬────┘        └─────────┘          PANTALLAS    COMPUTADORAS
             │                                    │            │
             ▼                                    ▼            ▼
        MOCASINES                                SONY        LENOVO
             │
             ▼
       FILTROS / TALLA
             │
             ▼
       CONFIGURACIÓN
             │
             └─────────────────────┐
                                   ▼
                           AGREGAR A BOLSA
                                   │
                                   ▼
                          VALIDAR CONTADOR
                                   │
                                   ▼
                             COMPRAR AHORA
```

---

 # Flujo negativo

```
             PRODUCTO
                │
                ▼
       ¿Sesión iniciada?
          │           │
         NO          SÍ
          │           │
          ▼           ▼
       LOGIN       ¿Datos
       FORM        obligatorios?
                      │
                  ┌───┴───┐
                 NO      SÍ
                  │        │
                  ▼        ▼
                ERROR    AGREGAR
                         A BOLSA
```

---

 # Flujo Edge Case

```
                 PRODUCTO
                    │
                    ▼
             Comprar ahora
                    │
                    ▼
            Capturar precio
            Capturar cantidad
                    │
                    ▼
                 REFRESH
                    │
                    ▼
            Validar información
                    │
              ┌─────┴─────┐
             OK           KO
              │            │
              ▼            ▼
            PASS         FAIL
```

 Otro escenario Edge:

```
Producto
   │
   ▼
Cantidad = 100
   │
   ▼
Agregar a bolsa
   │
   ▼
Validar disponibilidad
   │
   ▼
No debe existir
agregado exitoso
```

---

 # Resultados esperados

 La suite debe permitir identificar rápidamente problemas en los puntos críticos del proceso:

```
LOGIN
  │
  ▼
NAVEGACIÓN
  │
  ▼
FILTROS
  │
  ▼
SELECCIÓN
  │
  ▼
CONFIGURACIÓN
  │
  ▼
AGREGAR A BOLSA
  │
  ▼
VALIDAR BOLSA
  │
  ▼
COMPRAR AHORA
```

 Los escenarios negativos y edge permiten adicionalmente validar el comportamiento de la aplicación ante condiciones que no representan el flujo ideal.

---

 # Entregables

 El proyecto contempla:

```
Código fuente
     │
     ├── Page Objects
     ├── TestNG Tests
     ├── DriverManager
     ├── Utilities
     ├── testng.xml
     ├── pom.xml
     ├── reporter.json
     └── ExtentReports
```

 Reporte generado:

```
target/ExtentReports/RegressionReport.html
```

 Resultados Maven/Surefire:

```
target/surefire-reports/
```

---

 # Resultado

 El framework automatiza actualmente **6 escenarios prioritarios**:

```
┌─────────┬─────────────────────────────────────────────┐
│ TC-001  │ Login exitoso                               │
├─────────┼─────────────────────────────────────────────┤
│ TC-002  │ Flujo E2E con múltiples productos           │
├─────────┼─────────────────────────────────────────────┤
│ TC-003  │ Compra sin sesión activa                    │
├─────────┼─────────────────────────────────────────────┤
│ TC-004  │ Producto sin características obligatorias   │
├─────────┼─────────────────────────────────────────────┤
│ TC-005  │ Comprar ahora después de refresh            │
├─────────┼─────────────────────────────────────────────┤
│ TC-006  │ Cantidad superior a disponibilidad          │
└─────────┴─────────────────────────────────────────────┘


 Este README ya queda alineado con el código real de `PurchaseTest`: **no documenta casos que actualmente no existen** y deja explícito el riesgo de que los filtros de talla/color del listado no necesariamente se conserven en el detalle del producto.
