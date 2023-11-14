import {Pressable, StyleSheet, Text} from "react-native";
import {DYogaColors} from "../utils/DYogaColors";
import * as Progress from 'react-native-progress';

export default function DYogaButton({
                                        title = 'Button',
                                        backgroundColor = DYogaColors.buttonBackgroundColor,
                                        children,
                                        onPress = () => {
                                        },
                                        buttonTextStyles = {}, isLoading = false
                                    }) {

    const _defaultChild = <Text style={{...DYogaButtonStyles.buttonText, ...buttonTextStyles}}>
        {title}
    </Text>

    const _children = children ? children : _defaultChild

    const _onPress = () => {
        if (isLoading) return
        onPress()
    }

    return <Pressable onPress={_onPress} style={{...DYogaButtonStyles.container, backgroundColor}}>
        {isLoading && <Progress.Circle size={30} indeterminate={true} borderColor={DYogaColors.white} borderWidth={3} />}
        {!isLoading && _children}
    </Pressable>
}


const DYogaButtonStyles = StyleSheet.create({
    container: {
        width: 267,
        height: 54,
        borderRadius: 40,
        backgroundColor: DYogaColors.grey,
        alignItems: 'center',
        justifyContent: 'center',
    },
    buttonText: {
        fontFamily: "Inter",
        fontSize: 16,
        color: DYogaColors.buttonTextColor,
        fontWeight: "bold"
    }
})