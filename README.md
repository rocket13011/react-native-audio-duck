# 📦 react-native-audio-duck

A React Native **TurboModule** to play a `.mp3` sound while temporarily ducking the volume of other applications.
Compatible with **Android** and **iOS**.

---

## ✨ Features

- Play embedded `.mp3` sounds
- Temporarily lower system audio volume (ducking)
- Fully compatible with TurboModules, Fabric, Hermes
- Supports both Android and iOS

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

---

## ⚙️ iOS Setup

1. **Add `bell.mp3` to your Xcode project’s `Resources` folder**
2. **Ensure it's listed under**
   `Build Phases > Copy Bundle Resources`

---

## 🧠 Usage

### Import

```ts
import AudioDuck from 'react-native-audio-duck';
```

### Methods

#### `play(options: { fileName: string; duckOtherAudio: boolean }): Promise<void>`

```ts
await AudioDuck.play({
  fileName: 'bell',         // without file extension
  duckOtherAudio: true,     // enables system audio ducking
});
```

---

## 🧪 Example (React Native)

```ts
import { Button } from 'react-native';
import AudioDuck from 'react-native-audio-duck';

const MyComponent = () => (
  <Button
    title="Play Sound"
    onPress={() =>
      AudioDuck.play({ fileName: 'bell', duckOtherAudio: true })
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

- [ ] Dynamic/remote file support
- [ ] Configurable playback volume

## License
MIT

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
