package de.dkiefner.qapital.exercise.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import de.dkiefner.qapital.exercise.common.asset.AssetReader;
import de.dkiefner.qapital.exercise.common.asset.ReadAssetException;
import timber.log.Timber;

public class QapitalExerciseSQLiteHelper extends SQLiteOpenHelper {

	private Context context;

	public QapitalExerciseSQLiteHelper(@NonNull Context context) {
		super(context, "qapital_exercise", null, 2);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		AssetReader assetReader = new AssetReader(context);
		try {
			String initialSqlStatements = assetReader.read("init_db.sql");
			executeSqlStatements(db, initialSqlStatements);
		} catch (ReadAssetException e) {
			Timber.e(e, "Error creating initial database.");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		AssetReader assetReader = new AssetReader(context);

		for (int currentMigrationVersion = oldVersion + 1; currentMigrationVersion <= newVersion; currentMigrationVersion++) {
			try {
				String sqlMigrationStatements = assetReader.read("migration_v" + currentMigrationVersion + ".sql");
				executeSqlStatements(db, sqlMigrationStatements);
			} catch (ReadAssetException e) {
				Timber.w(e, "No database migration found for version %d.", currentMigrationVersion);
			}
		}
	}

	private void executeSqlStatements(SQLiteDatabase db, String sqlStatements) {
		String[] initialStatements = sqlStatements.split(";");
		for (String statement : initialStatements) {
			Timber.d("Execute statement: %s", statement);
			db.execSQL(statement);
		}
	}
}
