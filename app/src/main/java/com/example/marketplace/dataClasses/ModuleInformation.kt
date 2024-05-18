package com.example.marketplace.dataClasses

data class ModuleInformation(
    val modules: List<ModuleData> = listOf(
        ModuleData(
            1,
            "Оценка тестирования задач",
            "В данном блоке Вы познакомитесь с методами оценки задач и узнаете, на что необходимо закладывать время при оценивании. Узнаете правила работы со сроками, дедлайнами и оценками.",
            20,
            1,
            steps = listOf(
                StepData(1, "ManualTestingModule1Step1.pdf"),
                StepData(2, "Description 2"),
                StepData(3, "Description 3"),
                StepData(4, "Description 4"),
                StepData(5, "Description 5"),
                StepData(6, "Description 6"),
                StepData(7, "Description 7"),
                StepData(8, "Description 8"),
                StepData(9, "Description 9"),
                StepData(10, "Description 10"),
                StepData(11, "Description 11"),
            )
        ),
        ModuleData(
            2,
            "Введение2",
            "естественный язык и текст1",
            20,
            1,
            steps = listOf(StepData(2, "2"))
        ),
        ModuleData(
            3,
            "Введение3",
            "естественный язык и текст2",
            20,
            1,
            steps = listOf(StepData(3, "3"))
        ),
        ModuleData(
            4,
            "Введени4",
            "естественный язык и текст3",
            20,
            1,
            steps = listOf(StepData(4, "4"))
        ),
        ModuleData(
            5,
            "Введение1",
            "естественный язык и текст0",
            20,
            1,
            steps = listOf(StepData(5, "5"))
        ),
        ModuleData(
            6,
            "Введение2",
            "естественный язык и текст1",
            20,
            1,
            steps = listOf(StepData(6, "6"))
        ),
        ModuleData(
            7,
            "Введение3",
            "естественный язык и текст2",
            20,
            1,
            steps = listOf(StepData(7, "7"))
        ),
        ModuleData(
            9,
            "Введени4",
            "естественный язык и текст3",
            20,
            1,
            steps = listOf(StepData(8, "8"))
        )
    )
)
