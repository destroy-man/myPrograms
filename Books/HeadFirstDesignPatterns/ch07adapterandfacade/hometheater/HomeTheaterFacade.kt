package ru.korobeynikov.ch07adapterandfacade.hometheater

class HomeTheaterFacade(
    private val amp: Amplifier,
    private val tuner: Tuner,
    private val dvd: DvdPlayer,
    private val cd: CdPlayer,
    private val projector: Projector,
    private val lights: TheaterLights,
    private val screen: Screen,
    private val popper: PopcornPopper
) {

    private val history = StringBuilder()

    fun watchMovie(movie: String): String {
        history.clear()
        history.appendLine("Get ready to watch a movie...")
        history.appendLine(popper.on())
        history.appendLine(popper.pop())
        history.appendLine(lights.dim(10))
        history.appendLine(screen.down())
        history.appendLine(projector.on())
        history.appendLine(projector.wideScreenMode())
        history.appendLine(amp.on())
        history.appendLine(amp.setDvd(dvd))
        history.appendLine(amp.setSurroundSound())
        history.appendLine(amp.setVolume(5))
        history.appendLine(dvd.on())
        history.appendLine(dvd.play(movie))
        return history.toString()
    }

    fun endMovie(movie: String): String {
        history.clear()
        history.appendLine("Shutting movie theater down...")
        history.appendLine(popper.off())
        history.appendLine(lights.on())
        history.appendLine(screen.up())
        history.appendLine(projector.off())
        history.appendLine(amp.off())
        history.appendLine(dvd.stop(movie))
        history.appendLine(dvd.eject())
        history.appendLine(dvd.off())
        return history.toString()
    }
}