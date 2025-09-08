import android.app.Application
import com.example.desafio_mesas_comandas.data.local.AppDatabase
import com.example.desafio_mesas_comandas.data.local.TableEntity
import kotlinx.coroutines.flow.Flow

class TableRepository(private val application: Application) {
    private val dao = AppDatabase.getDatabase(application).tableDao()

    fun getAllTables(): Flow<List<TableEntity>> = dao.getAll()

    suspend fun tableCount(): Int {
        return dao.tableCount()
    }

    fun getFilteredTables(
        searchText: String?,
        activityType: String? = null
    ): Flow<List<TableEntity>> =
        dao.getFilteredTables(searchText, activityType)


    suspend fun upsertAll(tables: List<TableEntity>) {
        dao.upsertAll(tables)
    }
}
