package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.plus(interval: TimeInterval) : MyDate {
    return addTimeIntervals(interval, 1)
}

operator fun MyDate.plus(interval: RepeatedTimeInterval) : MyDate {
    return addTimeIntervals(interval.interval, interval.times)
}

operator fun TimeInterval.times(times: Int) : RepeatedTimeInterval {
    return RepeatedTimeInterval(this, times)
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val interval: TimeInterval, val times: Int)



class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var next: MyDate = start

            override fun hasNext(): Boolean {
                return next <= endInclusive
            }

            override fun next(): MyDate {
                val currentNext = next;
                next = next.nextDay()
                return currentNext;
            }
        }
    }
}
