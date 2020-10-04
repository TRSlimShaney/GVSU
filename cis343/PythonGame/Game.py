import sys, pygame
from time import sleep

from Sprites.Enemy import Enemy
from Sprites.Projectile import Projectile
from Sprites.Ship import Ship

pygame.init()
black = 0, 0, 0
white = 255, 255, 255

class Game():
    def __init__(self):
        self.screen = pygame.display.set_mode([960, 1280])
        # player's stuff
        self.ship = Ship()
        # game stuff
        self.screen.fill(black)
        self.enemies = pygame.sprite.Group()
        self.shots = pygame.sprite.Group()
        self.overlay = Overlay()
        self.score = 0
        self.lives = 3
        self.ready = True
        self.over = False
        self.clock = pygame.time.Clock()
        self.new_life_event = pygame.event.Event(pygame.USEREVENT + 1)
        # enemy stuff
        ufo = pygame.image.load('Sprites/ufoRed.png').convert()
        enemy = Enemy(ufo, 460, 80)
        self.enemies.add(enemy)
        for i in range(1, 4):
            enemy = Enemy(ufo, (i * 120) + 225, 200)
            self.enemies.add(enemy)
        for i in range(1, 6):
            enemy = Enemy(ufo, (i * 120) + 110, 320)
            self.enemies.add(enemy)
        pygame.mixer.music.load('wav/intro.wav')
        pygame.mixer.music.play(1)
        sleep(4)
        pygame.mixer.music.load('wav/drone.wav')
        pygame.mixer.music.play(-1)


class Overlay(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface((800, 20))
        self.rect = self.image.get_rect()
        self.font = pygame.font.Font(None, 50)
        self.render('Score: 0       Lives: 3')

    def render(self, text):
        self.text = self.font.render(text, True, white)
        self.image.blit(self.text, self.rect)

    def draw(self, screen):
        screen.blit(self.text, (0, 0))

    def update(self, score, lives):
        self.render('Score: ' + str(score) + '      Lives: ' + str(lives))


def main():
    game = Game()
    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                sys.exit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    game.ship.setSpeed(-10, 0)
                if event.key == pygame.K_RIGHT:
                    game.ship.setSpeed(10, 0)
                if event.key == pygame.K_SPACE:
                    shot = Projectile(game.ship.rect.left + 40, game.ship.rect.top - 20, 1)
                    game.shots.add(shot)
            else:
                game.ship.setSpeed(0, 0)
            if event.type == game.new_life_event.type:
                game.lives -= 1
                if game.lives > 0:
                    game.ship = Ship()
                else:
                    game.over = True
                    game.ship.rect.left = 2000
                    game.ship.rect.top = 2000
                    game.lives = 'Game Over'

        game.screen.fill(black)
        if game.over == False:
            game.ship.move()
            game.screen.blit(game.ship.image, game.ship.rect)
        for e in game.enemies:
            e.shoot(game)
            e.move()
            game.screen.blit(e.image, e.rect)
        for s in game.shots:
            s.move()
            s.update(game, game.enemies, game.ship)
            game.screen.blit(s.image, s.rect)
        game.overlay.update(game.score, game.lives)
        game.overlay.draw(game.screen)
        pygame.display.flip()
        game.clock.tick(60)


#  if script is run directly, execute main
if __name__=='__main__':
    main()