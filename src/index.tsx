import AudioDuck from './NativeAudioDuck';
import { Image } from 'react-native';

type PlayOptions = {
  fileName?: string;
  uri?: string | number; // number for require()
  duckOtherAudio?: boolean;
  volume?: number; // 0 to 1, default is 1
};

export function play(options: PlayOptions): Promise<void> {
  const { uri, ...rest } = options;

  if (typeof uri === 'number') {
    const assetSource = Image.resolveAssetSource(uri);
    return AudioDuck.play({ ...rest, uri: assetSource.uri });
  }

  return AudioDuck.play({ ...rest, uri });
}
