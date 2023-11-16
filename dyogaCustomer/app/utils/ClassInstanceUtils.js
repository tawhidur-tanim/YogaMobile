import moment from "moment";

export function orderClassInstances(classInstances) {
    return classInstances.sort((ci1, ci2) => {
        return new Date(ci2.classInstance.date) - new Date(ci1.classInstance.date);
    })
}

export function searchClassInstances(classInstances, filter, params = null) {
    return orderClassInstances(classInstances.filter(ci => (ci && ci.course && ci.classInstance) && (
            // time filter
            (ci.course.time && new moment(ci.course.time)
                .format('h:mm a').toLowerCase().includes(filter.toLowerCase()))

            // day of week filter
            || (ci.course.dayOfWeek && ci.course.dayOfWeek.toLowerCase().includes(filter.toLowerCase()))

            // room filter
            || (ci.classInstance.room && ci.classInstance.room.toLowerCase().includes(filter.toLowerCase()))

            // course type filter
            || (ci.course.type && ci.course.type.toLowerCase().includes(filter.toLowerCase()))

            // instructor filter
            || (ci.classInstance.instructor && ci.classInstance.instructor.toLowerCase().includes(filter.toLowerCase()))

            // date filter
            || (ci.classInstance.date && new moment(ci.classInstance.date)
                .format('Do MMMM YYYY').toLowerCase().includes(filter.toLowerCase()))
        )
    ))
}

export function isInClassesInstanceArray(classInstances, item) {
    return item && item.docId && classInstances && Object.keys(classInstances ?? {}).includes(item.docId)
}

export function filterClassInstancesByDate(classInstances, from, to) {
    let filtered = classInstances

    if(from) {
        filtered = filtered.filter(ci => {
            const selectedDateTime = new moment(ci.course.time)
            const fromDateTime = new moment(from)
            fromDateTime.date(selectedDateTime.date());
            fromDateTime.month(selectedDateTime.month());
            fromDateTime.year(selectedDateTime.year());
            return selectedDateTime.valueOf() >= fromDateTime.valueOf()
        })
    }

    if(to) {
        filtered = filtered.filter(ci => {
            const selectedDateTime = new moment(ci.course.time)
            const toDateTime = new moment(to)
            toDateTime.date(selectedDateTime.date());
            toDateTime.month(selectedDateTime.month());
            toDateTime.year(selectedDateTime.year());
            return selectedDateTime.valueOf() <= toDateTime.valueOf()
        })
    }

    return filtered
}