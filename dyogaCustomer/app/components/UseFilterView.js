import {Dimensions, Pressable, Animated, StyleSheet, Text, View} from "react-native";
import {DYogaColors} from "../utils/DYogaColors";
import {constDimensions, constStrings} from "../utils/Constants";
import DYogaButton from "./DYogaButton";
import {useState} from "react";
import DateTimePicker from '@react-native-community/datetimepicker';
import {convertTime} from "../utils/DateTimeUtils";

export default function UseFilterView({
                                          ctaPress,
                                          onToggle = () => {
                                          }, filterParams
                                      }) {

    const screenWidth = Dimensions.get('window').width;
    const screenHeight = Dimensions.get('window').height;

    const [date, setDate] = useState(new Date());
    const [params, setParams] = useState(filterParams ?? {
        bookFiltered: false,
        filterTimeFrom: null,
        filterTimeTo: null
    })
    const [mode, setMode] = useState('date');
    const [isStartTime, setIsStartTime] = useState(false);
    const [show, setShow] = useState(false);

    const onChange = (event, selectedDate) => {
        const currentDate = selectedDate;
        setShow(false);
        if (isStartTime) {
            setParams({
                ...params,
                filterTimeFrom: currentDate
            })
        } else {
            setParams({
                ...params,
                filterTimeTo: currentDate
            })
        }
    };

    const showMode = (currentMode) => {
        setShow(true);
        setMode(currentMode);
    };

    return <Pressable style={{...UseFilterViewStyles.container, height: screenHeight, width: screenWidth}}
                      onPress={onToggle}>
        <Animated.View style={{...UseFilterViewStyles.containerSheet, width: screenWidth}}>
            <View>
                <Text style={{...UseFilterViewStyles.yogaType}}>{constStrings.applyFilter}</Text>
            </View>
            <Text style={UseFilterViewStyles.infoText}>{"Toggle \"Booked\" to filter booked classes only"}</Text>
            <View style={{flexDirection: 'row'}}>
                <Pressable style={{
                    ...UseFilterViewStyles.bookedButton,
                    paddingHorizontal: 10, paddingVertical: 10,
                    backgroundColor: (params?.bookFiltered ? DYogaColors.bookedButtonBackgroundActive
                        : DYogaColors.bookedButtonBackground)
                }} onPress={() => setParams({...params, bookFiltered: !params?.bookFiltered})}>
                    <Text style={{
                        fontFamily: 'RobotoSlab',
                        color: (params?.bookFiltered ? DYogaColors.white : DYogaColors.grey65)
                    }}>{constStrings.booked}</Text>
                </Pressable>
            </View>

            <Text style={UseFilterViewStyles.infoText}>{"Time:"}</Text>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
                <Pressable style={{
                    ...UseFilterViewStyles.bookedButton,
                    backgroundColor: DYogaColors.bookedButtonBackground,
                    paddingHorizontal: 10, paddingVertical: 10
                }} onPress={() => {
                    setIsStartTime(true)
                    showMode('time')
                }}>
                    <Text style={{
                        fontFamily: 'RobotoSlab',
                        color: DYogaColors.grey65
                    }}>{params?.filterTimeFrom ? convertTime(params?.filterTimeFrom) : "--:--"}</Text>
                </Pressable>
                <Text style={{marginHorizontal: 10}}>to</Text>
                <Pressable style={{
                    ...UseFilterViewStyles.bookedButton,
                    backgroundColor: DYogaColors.bookedButtonBackground,
                    paddingHorizontal: 10, paddingVertical: 10
                }} onPress={() => {
                    setIsStartTime(false)
                    showMode('time')
                }}>
                    <Text style={{
                        fontFamily: 'RobotoSlab',
                        color: DYogaColors.grey65
                    }}>{params?.filterTimeTo ? convertTime(params?.filterTimeTo) : "--:--"}</Text>
                </Pressable>
            </View>
            <View style={{...UseFilterViewStyles.cta, justifyContent: 'center', marginTop: 40}}>
                <DYogaButton title={constStrings.filter} onPress={() => ctaPress(params)}/>
            </View>

            {show && (<DateTimePicker testID="dateTimePicker" value={date} mode={mode}
                                      is24Hour={false} onChange={onChange}/>)}
        </Animated.View>
    </Pressable>
}

const UseFilterViewStyles = StyleSheet.create({
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
        fontSize: 12,
        marginTop: 20
    },
    bookedButton: {
        marginTop: 5,
        fontFamily: "RobotoSlab",
        paddingVertical: 3,
        paddingHorizontal: 5,
        borderRadius: 5
    },
})