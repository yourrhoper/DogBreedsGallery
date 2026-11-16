package com.example.dogbreedsgallery.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `breed_images` (
                `id` TEXT NOT NULL,
                `breedId` INTEGER NOT NULL,
                `url` TEXT NOT NULL,
                PRIMARY KEY(`id`, `breedId`),
                FOREIGN KEY(`breedId`) REFERENCES `breeds`(`id`)
                ON UPDATE NO ACTION ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE INDEX IF NOT EXISTS `index_breed_images_breedId`
            ON `breed_images` (`breedId`)
            """.trimIndent()
        )
    }
}
