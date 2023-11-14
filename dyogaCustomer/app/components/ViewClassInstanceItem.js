import {Dimensions, Pressable, StyleSheet, Text, View} from "react-native";
import {DYogaColors} from "../utils/DYogaColors";
import {constDimensions, constStrings} from "../utils/Constants";
import DYogaButton from "./DYogaButton";
import moment from "moment/moment";

export default function ViewClassInstanceItem({item = {}, ctaPress, ctaTitle = constStrings.addToCart, onToggle = () => {}}) {

    const screenWidth = Dimensions.get('window').width;
    const screenHeight = Dimensions.get('window').height;

    const {type, dayOfWeek,duration, time, price, description} = item.course;
    const {instructor, room, comments} = item.classInstance;

    // todo: format `time` properly
    // todo: convert `duration` to hours when greater/equal to 60 minutes

    return <Pressable style={{...ViewClassInstanceItemStyles.container, height: screenHeight, width: screenWidth}} onPress={onToggle}>
        <View style={{...ViewClassInstanceItemStyles.containerSheet, width: screenWidth}}>
            <View>
                <Text style={{...ViewClassInstanceItemStyles.yogaType}}>{type}</Text>
            </View>
            <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>Day of the week</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{dayOfWeek}</Text>
                </View>
                <View>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>Time</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{new moment(time).format('h:mm a')}</Text>
                </View>
            </View>
            <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>Duration</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{duration}</Text>
                </View>
                <View>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>Price</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>£{price}</Text>
                </View>
            </View>
            <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>Instructor</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{instructor}</Text>
                </View>
                <View>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>Room</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{room}</Text>
                </View>
            </View>
            {description && <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>Description</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{description}</Text>
                </View>
            </View>}
            {ctaPress && <View style={{...ViewClassInstanceItemStyles.cta, justifyContent: 'center', marginTop: 40}}>
                <DYogaButton title={ctaTitle} onPress={ctaPress}/>
            </View>}
        </View>
    </Pressable>
}

const ViewClassInstanceItemStyles = StyleSheet.create({
    container: {
        flex: 1,
        position: 'absolute',
        backgroundColor: DYogaColors.screenBackgroundBlur,
        paddingLeft: constDimensions.screenPaddingHorizontal,
        paddingRight: constDimensions.screenPaddingHorizontal,
        width: '100%',
        zIndex: 999,
    },
    containerSheet: {
        position: 'absolute',
        backgroundColor: DYogaColors.white,
        bottom: 0,
        padding: 30,
        borderTopLeftRadius: 50,
        borderTopRightRadius: 50,
    },
    cta: {
        flexDirection: 'row',
        marginBottom: 20
    },
    row: {
        flexDirection: 'row',
        marginTop: 20
    },
    column: {
        width: '50%',
        flexDirection: 'column',
    },
    label: {
        fontFamily: "RobotoSlab",
        color: DYogaColors.grey,
        fontSize: 12,
        paddingBottom: 7
    },
    yogaType: {
        fontFamily: 'RobotoSlabBold',
        fontWeight: "300",
        fontSize: 24
    },
    infoText: {
        fontFamily: 'RobotoSlab',
        marginRight: 5,
        marginBottom: 2,
        fontSize: 12
    }
})