import { TurboModuleRegistry } from 'react-native';

export interface Spec {
  play(options: { fileName: string; duckOtherAudio: boolean }): Promise<void>;
}

// @ts-ignore
export default TurboModuleRegistry.getEnforcing<Spec>('AudioDuck');
