import {Dimensions, Pressable, Animated, StyleSheet, Text, View} from "react-native";
import {DYogaColors} from "../utils/DYogaColors";
import {constDimensions, constStrings} from "../utils/Constants";
import DYogaButton from "./DYogaButton";
import moment from "moment/moment";
import {convertDuration} from "../utils/DateTimeUtils";


export default function ViewClassInstanceItem({
                                                  navigation,
                                                  item = {},
                                                  ctaPress,
                                                  ctaTitle = constStrings.addToCart,
                                                  onToggle = () => {
                                                  },
                                                  isInCart = false,
                                                  isBooked = false,
                                              }) {

    const screenWidth = Dimensions.get('window').width;
    const screenHeight = Dimensions.get('window').height;

    const {type, dayOfWeek, duration, time, price, description} = item.course;
    const {instructor, room, comments} = item.classInstance;

    return <Pressable style={{...ViewClassInstanceItemStyles.container, height: screenHeight, width: screenWidth}}
                      onPress={onToggle}>
        <Animated.View style={{...ViewClassInstanceItemStyles.containerSheet, width: screenWidth}}>
            <View>
                <Text style={{...ViewClassInstanceItemStyles.yogaType}}>{type}</Text>
            </View>
            <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>{constStrings.dayOfWeek}</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{dayOfWeek}</Text>
                </View>
                <View>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>{constStrings.time}</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{new moment(time).format('h:mm a')}</Text>
                </View>
            </View>
            <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>{constStrings.duration}</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{convertDuration(duration)}</Text>
                </View>
                <View>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>{constStrings.price}</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>£{price}</Text>
                </View>
            </View>
            <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>{constStrings.instructor}</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{instructor}</Text>
                </View>
                <View>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>{constStrings.room}</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{room}</Text>
                </View>
            </View>
            {description && <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>{constStrings.description}</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{description}</Text>
                </View>
            </View>}
            {comments && <View style={ViewClassInstanceItemStyles.row}>
                <View style={ViewClassInstanceItemStyles.column}>
                    <Text style={{...ViewClassInstanceItemStyles.label}}>{constStrings.comments}</Text>
                    <Text style={{...ViewClassInstanceItemStyles.infoText}}>{comments}</Text>
                </View>
            </View>}
            {(isBooked || isInCart) &&
                <View style={{...ViewClassInstanceItemStyles.cta, justifyContent: 'center', marginTop: 40}}>
                    {isInCart && <Text>{constStrings.addedToCartAlready}</Text>}
                    {isBooked && <Text>{constStrings.bookedClassAlready}</Text>}
                </View>}
            {isInCart && ctaPress &&
                <View style={{...ViewClassInstanceItemStyles.cta, justifyContent: 'center', marginTop: 4}}>
                    <DYogaButton title={constStrings.gotoCart} onPress={() => navigation.navigate("CheckoutScreen")}/>
                </View>}
            {!isBooked && !isInCart && ctaPress &&
                <View style={{...ViewClassInstanceItemStyles.cta, justifyContent: 'center', marginTop: 40}}>
                    <DYogaButton title={ctaTitle} onPress={ctaPress}/>
                </View>}
        </Animated.View>
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