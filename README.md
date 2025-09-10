
# Desafio Mobile Kotlin Pigz

Um desafio técnico proposto pela Pigz para o desenvolvimento de um aplicativo Android usando Kotlin e Jetpack Compose. A ideia do projeto é fazer o gerenciamento de mesas e comandas de um restaurante/Lachonete.


## Tabela de Conteúdos
- [Sobre](#sobre)
- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Instalação](#instalação)
- [Banco de dados (Room)](#banco-de-dados-(room))
- [Componentes Importantes](#componentes-importantes)
- [Fluxo de dados](#fluxo-de-dados)
- [Utilitários](#utilitários)
- [Como Executar o Projeto](#como-executar-o-projeto)

## Sobre
O Pigz Comanda é um aplicativo Android desenvolvido em Kotlin utilizando o Jetpack Compose para a interface do usuário, a arquitetura MVVM (Model-View-ViewModel) e o banco de dados Room para persistência local.O aplicativo tem como objetivo principal gerenciar mesas e comandas, simulando o fluxo de um sistema de pedidos em restaurantes.

## Tecnologias
- Linguagem: Kotlin
- UI Toolkit: Jetpack Compose
- Arquitetura: MVVM
- Controle de versão: Git e Github
- Banco de Dados: Room
- Desseralização do Mock: Gson
- Gerenciamento de dependências: Gradle (KTS)
- Coroutines & Flow: para chamadas assíncronas e reatividade

## Funcionalidades
- Listagem de mesas
- Filtros para busca
- Pesquisa avançada
- Detalhes da mesa

## Arquitetura
#### Model
Define a entidade (TableEntity) e modelos de dados(`CheckpadModel`).
#### ViewModel
Responsável por manter o estado da UI e interagir com o repositório(`MapViewModel`) e rodar a lógica de négocios da aplicação.
#### Repository
Camada intermediária que conecta o DAO (Room) com o ViewModel(`TableRepository`).
#### View(UI)
Implementada em Jetpack Compose, com componentes reutilizáveis(`CardTable`, `TablesGrid`, `SearchBarCustom` e etc...).

## Estrutura de Pastas 

```bash]
app/src/main/java/com/example/desafio_mesas_comandas/
│── data/
│   ├── local/
│   │   ├── TableEntity.kt        # Entidade da Room
│   │   ├── TableDao.kt           # DAO para operações no BD
│   │   ├── TableDatabase.kt      # Classe do banco de dados Room
│   └── repository/
│       └── TableRepository.kt    # Repositório para abstração de dados
│
│── model/
│   ├── Seller.kt                 # Modelo de vendedor
│   └── CheckpadApiResponse.kt    # Modelo de resposta mock
│
│── utils/
│   └── ReadJson.kt               # Utilitário para ler mocks JSON
│   └── MasksHelp.kt              # Utilitário para mascarar dados
│
│── view/
│   ├── MainActivity.kt           # Tela principal
│   └── components/
│       ├── TableList.kt          # Lista de mesas (Composable)
│       └── TableItem.kt          # Item individual (Composable)
│       └── ...
│
│── viewmodel/
│   └── TableViewModel.kt         # ViewModel principal
│
│── ui/theme/
│   ├── Color.kt                  # Definições de cores
│   ├── Theme.kt                  # Configuração de tema Material3
│   └── Type.kt                   # Definições de tipografia


```


## Instalação
Passos pra rodar localmente:
```bash
git clone https://github.com/KylixXD/Desafio_mobile_Kotlin.git
cd desafio_mesas_comandas 
./gradlew build
```

### Banco de dados (Room)

## Entity
Contém as entidades(tabelas) usadas para a representação de uma mesa e comandas.
```bash
@Entity
data class TableEntity(
    @PrimaryKey val id: Int, 
    val title: Int, // Número da mesa
    val customerName: String?, // Nome do cliente
    val orderCount: Int, // Número de pedidos
    val idleTime: Int, // Tempo ocioso
    val activity: String, // Status das mesas
    val sellerName: String, // Nome do Vendedor
    val subTotal: Int?, // Total do pedido
    val numberCustomer: Int? // Quantidade de clientes na mesa
)
```

## DAO
Fornece métodos para acessar e manipular os dados da tabela.`Checkpad_Tables` no banco de dados local. Inclui operações para buscar paginada, busca avançada e contagem de mesas.
```bash
@Dao
interface TableDao {

    @Query(
        """
        SELECT * FROM `CheckPad_Tables`
        WHERE (:searchText IS NULL 
               OR CAST(title AS TEXT) LIKE '%' || :searchText || '%' 
               OR customerName LIKE '%' || :searchText || '%' 
               OR sellerName LIKE '%' || :searchText || '%')
          AND (:activityType IS NULL OR activity = :activityType)
        ORDER BY title ASC
    """
    )
    fun getTablesPagingSource(
        searchText: String?,
        activityType: String?
    ): PagingSource<Int, TableEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<TableEntity>)

    @Query("SELECT * FROM `CheckPad_Tables` ORDER BY title ASC")
    fun getAll(): Flow<List<TableEntity>>

    @Query("SELECT COUNT(id) FROM `CheckPad_Tables`")
    suspend fun tableCount(): Int


}
```

## Database
Esta classe abstrata serve como o principal ponto de acesso para a conexão com o banco de dados persistente. Ela segue o padrão Singleton para garantir que apenas uma instância do banco de dados seja criada durante todo o ciclo de vida do aplicativo.
 * @property entities Lista das classes de entidade que fazem parte deste banco de dados.
 * @property version é a versão atual do esquema do banco de dados. Deve ser incrementada a cada alteração no esquema, e uma migração correspondente deve ser fornecida.
 * @property exportSchema Define se o esquema deve ser exportado para um arquivo JSON na  pasta do projeto.
```bash
@Database(entities = [TableEntity::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tableDao(): TableDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE `CheckPad Tables` ADD COLUMN numberCustomer INTEGER")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                 db.execSQL("ALTER TABLE `CheckPad Tables` RENAME TO `CheckPad_Tables`")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Tables_Database.db"
                )
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
)
```

## Componentes Importantes
* HomePage.kt &rarr; Pagina inicial do App.
* MapScreen.kt &rarr; Página de mapeamento das mesas.
* ConfigurationPage.kt &rarr; Pagina de configurações.
* TablesGrid.kt &rarr; Exibe as mesas em formato de Grid.
* CardTable.kt &rarr; Componente visual da mesa com os dados da mesa.
* SearchBarCustom.kt &rarr; Filtro de Pesquisa de mesas.

## Fluxo de dados

1. **Carregamento inicial**
- O aplicativo inicia em `MainActivity`.
- O `TableViewModel` é instanciado.
- Dados são buscados do Room ou, se necessário, do mock JSON (`ReadJson.kt`).
2. **Banco de Dados (Room)**
- `TableEntity` define a estrutura da tabela.
- `TableDao` expõe queries (ex: buscar mesas, contar pedidos e etc..).
- `TableDatabase` fornece acesso ao Room Database.
3. **Repository** 
- O `TableRepository` decide de onde os dados vêm (Room ou JSON).
- Ele expõe fluxos (`Flow`) para o `TableViewModel`.
4. **ViewModel**.
- O TableViewModel escuta os dados do Repository.
- Expõe os estados (`StateFlow` ou `LiveData`) para a UI.
5. **UI (Compose)**
- `TableList` recebe os dados da `ViewModel`.
- Renderiza os itens em `TableItem`.


## Utilitários

* ReadJson.kt &rarr; Lê arquivos Json mockados do assests, desserializa com Gson e transforma em objeto Java.
* MasksHelp.kt &rarr; Ajuda na formatalção de dados(máscaras).

## Como Executar o Projeto

### 1. Pré-requisitos
Antes de rodar o projeto, verifique se você tem instalado:
- [Android Studio](https://developer.android.com/studio) (versão mais recente recomendada)  
- [JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)  
- SDK do Android configurado no Android Studio  
- Emulador Android configurado **ou** um dispositivo físico conectado via USB  

---

### 2. Clonar o repositório
Clone o projeto para sua máquina local:
```bash
git clone https://github.com/usuario/Desafio_mobile_Kotlin.git
cd Desafio_mobile_Kotlin
```
### 3. Abrir no Android Studio
1. Abra o Android Studio
2. Clique em File > Open
3. Selecione a pasta do projeto `Desafio_mobile_Kotlin`
4. Aguarde o **Gradle Sync** finalizar (o Android Studio baixa as dependências automaticamente).

### 4. Executar o Projeto
- No topo da IDE, escolha o dispositivo de execução:
    - Emulador Android (Pixel, Nexus, etc.)
    -  Dispositivo físico (habilitar modo desenvolvedor e depuração USB)
- Clique no botão ▶️ Run (Shift + F10)   





