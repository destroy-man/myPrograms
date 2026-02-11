package ru.korobeynikov.ch11proxy.withproxy

class TimetableElectricTrainsProxy : TimetableTrains {

    private var timetableTrains: TimetableTrains? = TimetableElectricTrains()
    private var timetableCache: Array<String>? = null

    override fun getTimetable(info: String): Array<String>? {
        if (timetableCache == null) {
            timetableCache = timetableTrains?.getTimetable(info)
        }
        return timetableCache
    }

    override fun getTrainDepartureTime(trainId: String, info: String): String {
        if (timetableCache == null) {
            timetableCache = timetableTrains?.getTimetable(info)
        }
        timetableCache?.forEach { timetable ->
            if (timetable.startsWith("$trainId;")) {
                return timetable
            }
        }
        return ""
    }

    fun clearCache() {
        timetableTrains = null
    }
}