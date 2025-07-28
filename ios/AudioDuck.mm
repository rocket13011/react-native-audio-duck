#import "AudioDuck.h"
#import <AVFoundation/AVFoundation.h>

@interface AudioDuck () <AVAudioPlayerDelegate>
@property (nonatomic, strong) AVAudioPlayer *player;
@end

@implementation AudioDuck

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(play:(NSDictionary *)options
resolver:(RCTPromiseResolveBlock)resolve
rejecter:(RCTPromiseRejectBlock)reject)
{
NSString *fileName = options[@"fileName"];
BOOL duck = [options[@"duckOtherAudio"] boolValue];

NSError *error = nil;

AVAudioSessionCategoryOptions sessionOptions = duck
    ? AVAudioSessionCategoryOptionDuckOthers | AVAudioSessionCategoryOptionMixWithOthers
    : AVAudioSessionCategoryOptionMixWithOthers;

[[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback
                                 withOptions:sessionOptions
                                       error:&error];
[[AVAudioSession sharedInstance] setActive:YES error:&error];

NSString *path = [[NSBundle mainBundle] pathForResource:fileName ofType:@"mp3"];
if (path == nil) {
  NSLog(@"FILE_NOT_FOUND");
  reject(@"FILE_NOT_FOUND", [NSString stringWithFormat:@"Could not find %@.mp3 in main bundle", fileName], nil);
  return;
}
NSURL *url = [NSURL fileURLWithPath:path];
self.player = [[AVAudioPlayer alloc] initWithContentsOfURL:url error:&error];
self.player.delegate = self;
[self.player play];
resolve(nil);
}

- (void)audioPlayerDidFinishPlaying:(AVAudioPlayer *)player successfully:(BOOL)flag {
  [[AVAudioSession sharedInstance] setActive:NO error:nil];
}

@end
