import {
    Animated, Image, Pressable, StyleSheet, Text, View,
} from 'react-native';
import {DYogaColors} from "../utils/DYogaColors";
import {constStrings} from "../utils/Constants";

export default function ClassInstanceItem({item = {}, onPress, canDelete = false, onDelete}) {

    const _onPress = () => {
        onPress && onPress(item)
    }

    return <Pressable onPress={_onPress}>
        <Animated.View>
            <View style={{...ClassInstanceItemStyles.container, backgroundColor: '#0085FF'}}>
                <View style={ClassInstanceItemStyles.content}>
                    <Text style={ClassInstanceItemStyles.date}>24th November, 2023</Text>
                    <View style={ClassInstanceItemStyles.info}>
                        <Text
                            style={{
                                ...ClassInstanceItemStyles.infoText,
                                fontFamily: "RobotoSlabBold",
                                fontWeight: "600",
                                fontSize: 15,
                                marginBottom: 5
                            }}>
                            Family Yoga
                        </Text>
                    </View>
                    <View style={ClassInstanceItemStyles.info}>
                        <Text style={ClassInstanceItemStyles.infoText}>{constStrings.time}:</Text>
                        <Text style={ClassInstanceItemStyles.infoText}>Mavin James</Text>
                    </View>
                    <View style={ClassInstanceItemStyles.info}>
                        <Text style={ClassInstanceItemStyles.infoText}>{constStrings.instructor}:</Text>
                        <Text style={ClassInstanceItemStyles.infoText}>Mavin James</Text>
                    </View>
                </View>
                <Pressable onPress={canDelete ? onDelete : _onPress}>
                    {canDelete ? <Image style={{height: 24, width: 24, marginHorizontal: 10}}
                                        source={require("../../assets/icons/delete.png")}/> :
                        <Image style={{height: 24, width: 24, marginHorizontal: 10}}
                               source={require("../../assets/icons/clock.png")}/>}
                </Pressable>
            </View>
        </Animated.View>
    </Pressable>
}

const ClassInstanceItemStyles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        padding: 10,
        borderRadius: 5,
        width: '100%',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 15
    },
    content: {
        flexGrow: 1
    },
    date: {
        fontFamily: 'RobotoSlabBold',
        fontSize: 18,
        marginBottom: 5,
        color: DYogaColors.white
    },
    info: {
        flexDirection: 'row'
    },
    infoText: {
        fontFamily: 'RobotoSlab',
        color: DYogaColors.white,
        marginRight: 5,
        marginBottom: 2,
        fontSize: 12
    }
})