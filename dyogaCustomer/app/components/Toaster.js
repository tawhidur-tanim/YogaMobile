import {Platform, ToastAndroid} from "react-native";
import Toast from "react-native-toast-message";

const show = ({type = 'success', message, info}) => {
    if (Platform.OS === 'android') {
        ToastAndroid.show(message, ToastAndroid.LONG);
    } else {
        Toast.show({
            type,
            text1: message,
            text2: info
        });
    }
}

const Toaster = {
    show
}

export default Toaster