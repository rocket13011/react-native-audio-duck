# 📦 react-native-audio-duck

A React Native **TurboModule** to play a `.mp3` sound while temporarily ducking the volume of other applications.
Compatible with **Android** and **iOS**.

---

## ✨ Features

- Play embedded `.mp3` sounds
- Temporarily lower system audio volume (ducking)
- Fully compatible with TurboModules, Fabric, Hermes
- Supports both Android and iOS
- Supports files on react-native bundle (with require)
- Configurable playback volume

---

## 📦 Installation

```bash
yarn add react-native-audio-duck
```

---

## ⚙️ Android Setup

1. **Add your sound file to**:

```
android/src/main/res/raw/bell.mp3
```
or on the React Native bundle

---

## ⚙️ iOS Setup

1. **Add `bell.mp3` to your Xcode project’s `Resources` folder**
2. **Ensure it's listed under**
   `Build Phases > Copy Bundle Resources`

or on the React Native bundle

---

## 🧠 Usage

### Import

```ts
import AudioDuck from 'react-native-audio-duck';
```

### Methods

#### `play(options: { fileName?: string; duckOtherAudio: boolean, uri?: string | number, volume?: number}): Promise<void>`

fileName: Name of the sound file (without extension, e.g. `bell` for `bell.mp3`)
duckOtherAudio: Whether to duck other audio while playing this sound (true/false)
uri: Optional URI of the sound file (can be a local file or remote URL)
volume: Optional playback volume (0.0 to 1.0, default is 1

```ts
await play({
  fileName: 'bell',         // without file extension
  duckOtherAudio: true,     // enables system audio ducking
});
```
```ts
await play({
  uri: require('./assets/bell.mp3'),         // without file extension
  duckOtherAudio: true,     // enables system audio ducking
  volume: 0.5,              // optional playback volume (0.0 to 1.0)
});
```

---

## 🧪 Example (React Native)

```ts
import { Button } from 'react-native';
import { play } from 'react-native-audio-duck';

const MyComponent = () => (
  <Button
    title="Play Sound"
    onPress={() =>
      play({ fileName: 'bell', duckOtherAudio: true })
    }
  />
);
```

---

## 🛠️ Development

- JS Interface: `src/NativeAudioDuck.ts`
- Android Module: `AudioDuckModule.kt`
- Generated TurboModule: `NativeAudioDuckSpec.java`
- Run codegen:

```bash
yarn react-native codegen
```

---

## 🔍 Troubleshooting

### TurboModule not found

```
TurboModuleRegistry.getEnforcing(...): 'AudioDuck' could not be found
```

**Make sure**:
- Codegen has been run
- `NativeAudioDuckSpec.java` is generated
- `AudioDuckPackage()` is registered in native app
- No duplicate `AudioDuckSpec` exists (CMake conflict)

---

## ✅ TODO

- [X] Dynamic/remote file support
- [X] Configurable playback volume

## License
MIT

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
