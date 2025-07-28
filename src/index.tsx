import AudioDuck from './NativeAudioDuck';

export function play(fileName: string, duckOtherAudio: boolean): Promise<void> {
  return AudioDuck.play({ fileName, duckOtherAudio });
}
