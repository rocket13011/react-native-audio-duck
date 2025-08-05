/* global jest */
jest.mock('react-native-audio-duck', () => ({
  AudioDuck: {
    play: jest.fn(),
  },
}));
