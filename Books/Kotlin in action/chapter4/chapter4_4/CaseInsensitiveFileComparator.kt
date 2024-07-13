package ru.korobeynikov.chapter4.chapter4_4

import java.io.File

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File?, file2: File?): Int {
        return if (file1 != null && file2 != null)
            file1.path.compareTo(file2.path, ignoreCase = true)
        else if (file1 != null) -1
        else 1
    }
}