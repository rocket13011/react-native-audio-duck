import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  play(options: {
    fileName?: string;
    uri?: string; // string for remote URI or number for require() (not work with metro)
    duckOtherAudio?: boolean;
  }): Promise<void>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('AudioDuck');
