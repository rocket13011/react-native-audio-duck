import { Text, View, StyleSheet, Pressable } from 'react-native';
import { play } from 'react-native-audio-duck';

export default function App() {
  return (
    <View style={styles.container}>
      <Pressable
        onPress={() =>
          play({ fileName: 'bell', duckOtherAudio: true, volume: 0.2 })
        }
      >
        <Text>Play sound filename !</Text>
      </Pressable>
      <Pressable
        onPress={() =>
          play({ uri: require('./assets/bell.mp3'), duckOtherAudio: true })
        }
      >
        <Text>Play sound filename require !</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
