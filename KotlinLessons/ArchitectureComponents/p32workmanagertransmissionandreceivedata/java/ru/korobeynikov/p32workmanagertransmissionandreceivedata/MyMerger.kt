package ru.korobeynikov.p32workmanagertransmissionandreceivedata

import androidx.work.Data
import androidx.work.InputMerger

class MyMerger : InputMerger() {
    override fun merge(inputs: MutableList<Data>): Data {
        val output = Data.Builder()
        val mergedValues = HashMap<String, Any>()
        for (input in inputs)
            mergedValues.putAll(input.keyValueMap)
        output.putAll(mergedValues)
        output.putInt("input_data_count", inputs.size - 1)
        return output.build()
    }
}