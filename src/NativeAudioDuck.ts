import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  play(options: {
    fileName?: string;
    uri?: string; // string for remote URI or number for require() (not work with metro)
    duckOtherAudio?: boolean;
    volume?: number; // 0 to 1, default is 1
  }): Promise<void>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('AudioDuck');
