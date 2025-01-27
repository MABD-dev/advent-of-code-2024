

fun findOrder3(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
    val coursePre = MutableList(numCourses) { mutableListOf<Int>() }
    prerequisites.forEach { pair ->
        val (a, b) = pair
        coursePre[a].add(b)
    }

    val visited = MutableList(numCourses) { false }
    val finishedCourses = mutableListOf<Int>()

    fun canFinish(courseId: Int): Boolean {
        val preq = coursePre[courseId].ifEmpty { return true }
        if (finishedCourses.contains(courseId)) return true
        if (visited[courseId]) {
            return false
        }

        visited[courseId] = true
        preq.forEach { if (!canFinish(it)) return false }
        finishedCourses.add(courseId)

        return true
    }

    repeat(numCourses) { courseId ->
        if (!canFinish(courseId)) return false
    }

    return true
}

fun findOrder2(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
    val coursePre = MutableList(numCourses) { mutableListOf<Int>() }
    prerequisites.forEach { pair ->
        val (a, b) = pair
        coursePre[a].add(b)
    }

    val visited = MutableList(numCourses) { false }
    val finishedCourses = mutableListOf<Int>()

    fun canFinish(courseId: Int): Boolean {
        if (finishedCourses.contains(courseId)) return true
        if (visited[courseId]) {
            return false
        }

        visited[courseId] = true
        val canBeFinished = coursePre[courseId].all { preReq ->
            finishedCourses.contains(preReq) || canFinish(preReq)
        }

        if (!canBeFinished) return false
        finishedCourses.add(courseId)

        visited[courseId] = true
        return true
    }

    repeat(numCourses) { courseId ->
        if (!canFinish(courseId)) {
            return intArrayOf(-1)
        }
    }

    return finishedCourses.toIntArray()
        .also { println(it.joinToString(",")) }
}



fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
    val reqGrid = prerequisites.map { it.toList() }

    val coursesPreRequests = mutableMapOf<Int, MutableSet<Int>>()
    repeat(numCourses) { coursesPreRequests[it] = mutableSetOf() }

    reqGrid.forEach { request ->
        val (a, b) = request
        coursesPreRequests[a] = (coursesPreRequests.getOrDefault(a, mutableSetOf())).apply { add(b) }
    }

    if (coursesPreRequests.all { it.value.isNotEmpty() }) {
        return intArrayOf()
    }

    val output = mutableListOf<Int>()
    repeat(numCourses) { courseId ->
        if (coursesPreRequests[courseId]?.isEmpty() == true) {
            coursesPreRequests.remove(courseId)
            output.add(courseId)
        }
    }

    var didNoDoAnythingCounter = 0
    while (didNoDoAnythingCounter < 2) {
        var didRemoveAnything = false

        val courseIds = coursesPreRequests.keys
        for (currentCourseId in courseIds) {
            val coursePreShit = coursesPreRequests[currentCourseId]?.toSet() ?: continue //.getOrDefault(currentCourseId, setOf()).toSet()

            for (requiredCourseId in coursePreShit) {
                if (coursesPreRequests.getOrDefault(requiredCourseId, setOf()).isEmpty()) {
                    coursesPreRequests[currentCourseId]!!.remove(requiredCourseId)
                    didRemoveAnything = true
                }
            }

            if (coursesPreRequests[currentCourseId]?.isEmpty() == true) {
                if (!output.contains(currentCourseId)) {
                    output.add(currentCourseId)
                }
            }

        }
        if (!didRemoveAnything) didNoDoAnythingCounter++
        else didNoDoAnythingCounter = 0

        if (coursesPreRequests.all { it.value.isEmpty() }) break
    }

    if (output.size != numCourses) {
        return intArrayOf()
    }

    return output.toIntArray()
        .also { println(it.joinToString(",")) }
}


fun main() {
    findOrder3(
        4,
        arrayOf(
            intArrayOf(1, 0),
            intArrayOf(2, 0),
            intArrayOf(3, 1),
            intArrayOf(3, 2),
        )
    ).println()

    findOrder3(
        2,
        arrayOf(
            intArrayOf(0, 1),
        )
    ).println()

    findOrder3(
        3,
        arrayOf(
            intArrayOf(1, 0),
            intArrayOf(1, 2),
            intArrayOf(0, 1)
        )
    ).println()
}