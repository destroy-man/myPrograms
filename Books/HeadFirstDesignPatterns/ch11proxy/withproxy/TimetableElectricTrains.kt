package ru.korobeynikov.ch11proxy.withproxy

class TimetableElectricTrains : TimetableTrains {

    override fun getTimetable(info: String): Array<String> {
        val list = info.split("\n")
        return list.toTypedArray()
    }

    override fun getTrainDepartureTime(trainId: String, info: String): String {
        val timetables = getTimetable(info)
        timetables.forEach { timetable ->
            if (timetable.startsWith("$trainId;")) {
                return timetable
            }
        }
        return ""
    }
}