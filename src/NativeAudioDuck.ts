import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  play(options: { fileName: string; duckOtherAudio: boolean }): Promise<void>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('AudioDuck');
