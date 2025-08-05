#import "AudioDuck.h"
#import <AVFoundation/AVFoundation.h>
#import <React/RCTConvert.h>

@interface AudioDuck () <AVAudioPlayerDelegate>
@property (nonatomic, strong) AVAudioPlayer *player;
@end

@implementation AudioDuck

RCT_EXPORT_MODULE()

- (void)play:(JS::NativeAudioDuck::SpecPlayOptions &)options
     resolve:(nonnull RCTPromiseResolveBlock)resolve
      reject:(nonnull RCTPromiseRejectBlock)reject
{
  NSString *fileName = options.fileName();
  NSString *uri = options.uri();
  std::optional<bool> duckOpt = options.duckOtherAudio();
  double volume = options.volume().has_value() ? options.volume().value() : 1.0;
  NSLog(@"[AudioDuck] volume option received: %f", volume);

  BOOL duck = duckOpt.has_value() ? duckOpt.value() : NO;

  NSError *error = nil;
  AVAudioSessionCategoryOptions sessionOptions = duck
      ? AVAudioSessionCategoryOptionDuckOthers | AVAudioSessionCategoryOptionMixWithOthers
      : AVAudioSessionCategoryOptionMixWithOthers;

  [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback
                                   withOptions:sessionOptions
                                         error:&error];
  [[AVAudioSession sharedInstance] setActive:YES error:&error];

  NSURL *url = nil;

  if (uri != nil) {
    url = [NSURL fileURLWithPath:uri];
  } else if (fileName != nil) {
    NSString *path = [[NSBundle mainBundle] pathForResource:fileName ofType:@"mp3"];
    if (path != nil) {
      url = [NSURL fileURLWithPath:path];
    }
  }

  if (url == nil) {
    reject(@"FILE_NOT_FOUND", @"You must provide either `uri`, `requireUri` or `fileName`", nil);
    return;
  }

  self.player = [[AVAudioPlayer alloc] initWithContentsOfURL:url error:&error];
  if (error) {
    reject(@"PLAYER_ERROR", error.localizedDescription, error);
    return;
  }
  
  [self.player prepareToPlay];
  self.player.volume = volume;
  self.player.delegate = self;
  [self.player play];
}

- (void)audioPlayerDidFinishPlaying:(AVAudioPlayer *)player successfully:(BOOL)flag {
  [[AVAudioSession sharedInstance] setActive:NO error:nil];
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:(const facebook::react::ObjCTurboModule::InitParams &)params
{
  return std::make_shared<facebook::react::NativeAudioDuckSpecJSI>(params);
}

@end
