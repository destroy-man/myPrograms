package ru.korobeynikov.ch12compoundpatterns.factory

import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable
import ru.korobeynikov.ch12compoundpatterns.goose.Goose
import ru.korobeynikov.ch12compoundpatterns.goose.GooseAdapter

class GooseFactory : AbstractGooseFactory() {
    override fun createGoose(): Quackable {
        return GooseAdapter(Goose())
    }
}