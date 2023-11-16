import {constStrings} from "./Constants";
import moment from "moment";

export function convertDuration(duration) {
    if (duration < 60) return duration;

    const hours = (duration / 60);
    const realHours = Math.floor(hours);
    const minutes = (hours - realHours) * 60;
    const realMinutes = Math.round(minutes);

    return constStrings.durationFormat.replace("%1s", realHours).replace("%2s", realMinutes);
}

export function convertTime(time) {
    return new moment(time).format('h:mm a')
}