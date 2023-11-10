import {Button, Pressable, StyleSheet, Text} from "react-native";
import {DYogaColors} from "../utils/DYogaColors";

export default function DYogaButton({title = 'Button', backgroundColor = DYogaColors.buttonBackgroundColor, children}) {

    const _defaultChild = <Text style={DYogaButtonStyles.buttonText}>
        {title}
    </Text>

    const _children = children ? children : _defaultChild

    return <Pressable style={{...DYogaButtonStyles.container, backgroundColor}}>
        {_children}
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
    buttonText : {
        color: DYogaColors.buttonTextColor,
        fontWeight: "bold"
    }
})