package com.example.minisportapp.tasks

import android.os.AsyncTask

class SportTaskFactory {
    fun <InputParameter: Any, OutputParameter: Any?> createTask(
        taskFunction: (InputParameter)->OutputParameter,
        onResult: (OutputParameter) -> Unit) : SportTask<InputParameter, OutputParameter> {

        return AsyncSportTask(taskFunction, onResult)
    }
}

interface SportTask<InputParameter: Any, OutputParameter: Any?> {
    fun executeTask(input: InputParameter)
}

class AsyncSportTask<InputParameter: Any, OutputParameter: Any?>(
    private val taskFunction: (InputParameter)->OutputParameter,
    private val onResult: (OutputParameter) -> Unit):
        SportTask<InputParameter, OutputParameter>,
        AsyncTask<InputParameter, Int, OutputParameter>() {

    override fun executeTask(input: InputParameter) {
        execute(input)
    }

    override fun doInBackground(vararg input: InputParameter): OutputParameter {
        return taskFunction(input[0])
    }

    override fun onPostExecute(result: OutputParameter) {
        onResult(result)
    }
}

@Suppress("unused")
class ExampleTaskUsage {
    fun example() {
        val factory = SportTaskFactory()
        factory.createTask(::taskFunction, ::onResult)
    }

    private fun taskFunction(input: Integer): String? {
        return "The input was $input and this is the output"
    }

    private fun onResult(result: String?) {
        println(result)
    }
}