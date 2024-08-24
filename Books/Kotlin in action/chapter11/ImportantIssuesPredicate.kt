package ru.korobeynikov.chapter11

class ImportantIssuesPredicate(private val project: String) : (Issue) -> Boolean {

    override fun invoke(issue: Issue) = issue.project == project && issue.isImportant()

    private fun Issue.isImportant() =
        type == "Bug" && (priority == "Major" || priority == "Critical")
}