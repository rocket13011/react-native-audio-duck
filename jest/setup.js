/* global jest */
jest.mock('react-native-audio-duck', () => ({
  play: jest.fn(),
}));
