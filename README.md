
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

```bash
desafio_mesas_comandas/
│── local/               # Persistência (Room)
│   ├── AppDatabase.kt
│   ├── TableDao.kt
│   └── TableEntity.kt
│
│── repository/          # Repositório (camada de acesso a dados)
│   └── TableRepository.kt
│
│── model/               # Modelos de dados
│   └── CheckpadModel.kt
│
│── components/          # Componentes Jetpack Compose reutilizáveis
│   ├── CardTable.kt
│   ├── TablesGrid.kt
│   ├── SearchBarCustom.kt
│   └── ...
│
│── viewmodel/           # Lógica de apresentação
│   └── MapViewModel.kt
│
│── view/                # Telas da aplicação
│   ├── HomePage.kt
│   ├── MapScreen.kt
│   ├── ConfigurationPage.kt
│   └── MyApp.kt
│
│── utils/               # Funções utilitárias
│   ├── ReadJson.kt
│   └── MasksHelp.kt
│
│── theme/               # Tema do app (Material 3)
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
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
Campos usados na visualização dos cards das mesas.
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
Fornece métodos para acessar e manipular os dados da tabela `Checkpad_Tables` no banco de dados local. Inclui operações para buscar paginada, busca avançada e contagem de mesas.
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

1. A **UI(View)** solicita dados ao **ViewModel**.
2. O **ViewModel** consulta o **Repository**.
3. O **Repository** decide se busca no **DAO(Room)** ou em fonte externa(**Mock Json**).
4. Os dados são retornados via **Flow** para o **ViewModel**.
5. O **ViewModel** atualiza o estado observado pela **View**.
6. A **UI Compose** se recompõe automaticamente.

## Utilitários

* ReadJson.kt &rarr; Lê arquivos Json mockados do assests, desserializa com Gson e transforma em objeto Java.
* MasksHelp.kt &rarr; Ajuda na formatalção de dados(máscaras).





