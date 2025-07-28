import { Text, View, StyleSheet, Pressable } from 'react-native';
import { play } from 'react-native-audio-duck';

export default function App() {
  return (
    <View style={styles.container}>
      <Pressable onPress={() => play('bell', true)}>
        <Text>Play sound !</Text>
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
