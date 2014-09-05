#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
#ifndef MAP_FILE
#define MAP_FILE MAP_SHARED
#endif
int fsize(int fd) {
  struct stat stat;
  int res = fstat(fd,&stat);
  return stat.st_size;
}
int printll(char* s) {
  while (*s != '\n' && *s != ',' && *s != '\t') {
    putchar(*s++);
  }
  return 0;
}
unsigned long hash(unsigned char *str) // FIXME: need to take length!
{
  unsigned long hash = 5381;
  int c;
  while ((c = *str++))
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
void Snippet(char*);
int main(int argc, char *argv[])
{
  if (argc != 2) {
    printf("usage: query <filename>\n");
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char*  x0) {
  printf("%s\n","Phrase,Year,MatchCount,VolumeCount,Word,Value");
  int32_t x2 = 0;
  int32_t x3 = 0;
  int32_t* x4 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x6=0; x6 < 256; x6++) {
    x4[x6] = -1;
  }
  char** x10 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x11 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x12 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x13 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x14 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x15 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x16 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x17 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x18 = 0;
  int32_t x19 = 0;
  int32_t* x20 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x21 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x25 = 0;
  int32_t x22 = open(x0,0);
  int32_t x23 = fsize(x22);
  char* x24 = mmap(0, x23, PROT_READ, MAP_FILE | MAP_SHARED, x22, 0);
  int32_t x87 = (int32_t)0L;
  int32_t x88 = x87 & 255;
  bool x94 = !true;
  for (;;) {
    int32_t x26 = x25;
    bool x27 = x26 < x23;
    if (!x27) break;
    int32_t x29 = x25;
    for (;;) {
      int32_t x30 = x25;
      char x31 = x24[x30];
      bool x32 = x31 != '\t';
      if (!x32) break;
      x25 += 1;
    }
    int32_t x37 = x25;
    x25 += 1;
    int32_t x41 = x25;
    for (;;) {
      int32_t x42 = x25;
      char x43 = x24[x42];
      bool x44 = x43 != '\t';
      if (!x44) break;
      x25 += 1;
    }
    int32_t x49 = x25;
    x25 += 1;
    int32_t x53 = x25;
    for (;;) {
      int32_t x54 = x25;
      char x55 = x24[x54];
      bool x56 = x55 != '\t';
      if (!x56) break;
      x25 += 1;
    }
    int32_t x61 = x25;
    x25 += 1;
    int32_t x65 = x25;
    for (;;) {
      int32_t x66 = x25;
      char x67 = x24[x66];
      bool x68 = x67 != '\n';
      if (!x68) break;
      x25 += 1;
    }
    int32_t x73 = x25;
    x25 += 1;
    int32_t x77 = x19;
    char* x40 = x24+x29;
    x10[x77] = x40;
    int32_t x38 = x37 - x29;
    x11[x77] = x38;
    char* x52 = x24+x41;
    x12[x77] = x52;
    int32_t x50 = x49 - x41;
    x13[x77] = x50;
    char* x64 = x24+x53;
    x14[x77] = x64;
    int32_t x62 = x61 - x53;
    x15[x77] = x62;
    char* x76 = x24+x65;
    x16[x77] = x76;
    int32_t x74 = x73 - x65;
    x17[x77] = x74;
    x19 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x89 = x88;
    for (;;) {
      int32_t x90 = x89;
      int32_t x91 = x4[x90];
      bool x93 = x91 == -1;
      bool x95;
      if (x93) {
        x95 = false;
      } else {
        x95 = x94;
      }
      if (!x95) break;
      int32_t x97 = x89;
      int32_t x98 = x97 + 1;
      int32_t x99 = x98 & 255;
      x89 = x99;
    }
    int32_t x103 = x89;
    int32_t x104 = x4[x103];
    bool x105 = x104 == -1;
    int32_t x111;
    if (x105) {
      int32_t x106 = x3;
      x3 += 1;
      x4[x103] = x106;
      x21[x106] = 0;
      x111 = x106;
    } else {
      x111 = x104;
    }
    int32_t x113 = x111;
    //#hash_lookup
    int32_t x114 = x21[x113];
    int32_t x115 = x113 * 256;
    int32_t x116 = x115 + x114;
    x20[x116] = x77;
    int32_t x118 = x114 + 1;
    x21[x113] = x118;
  }
  int32_t x126 = 0;
  int32_t x123 = open("src/data/words.csv",0);
  int32_t x124 = fsize(x123);
  char* x125 = mmap(0, x124, PROT_READ, MAP_FILE | MAP_SHARED, x123, 0);
  for (;;) {
    int32_t x127 = x126;
    char x128 = x125[x127];
    bool x129 = x128 != ',';
    if (!x129) break;
    x126 += 1;
  }
  int32_t x134 = x126;
  x126 += 1;
  int32_t x137 = x126;
  int32_t x138 = 0;
  for (;;) {
    int32_t x139 = x126;
    char x140 = x125[x139];
    bool x141 = x140 != '\n';
    if (!x141) break;
    int32_t x143 = x138;
    int32_t x145 = x126;
    int32_t x144 = x143 * 10;
    char x146 = x125[x145];
    char x147 = x146 - '0';
    int32_t x148 = x144 + x147;
    x138 = x148;
    x126 += 1;
  }
  x126 += 1;
  int32_t x154 = x138;
  for (;;) {
    int32_t x155 = x126;
    bool x156 = x155 < x124;
    if (!x156) break;
    int32_t x158 = x126;
    for (;;) {
      int32_t x159 = x126;
      char x160 = x125[x159];
      bool x161 = x160 != ',';
      if (!x161) break;
      x126 += 1;
    }
    int32_t x166 = x126;
    x126 += 1;
    int32_t x170 = x126;
    int32_t x171 = 0;
    for (;;) {
      int32_t x172 = x126;
      char x173 = x125[x172];
      bool x174 = x173 != '\n';
      if (!x174) break;
      int32_t x176 = x171;
      int32_t x178 = x126;
      int32_t x177 = x176 * 10;
      char x179 = x125[x178];
      char x180 = x179 - '0';
      int32_t x181 = x177 + x180;
      x171 = x181;
      x126 += 1;
    }
    x126 += 1;
    int32_t x187 = x171;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x188 = x88;
    for (;;) {
      int32_t x189 = x188;
      int32_t x190 = x4[x189];
      bool x192 = x190 == -1;
      bool x193;
      if (x192) {
        x193 = false;
      } else {
        x193 = x94;
      }
      if (!x193) break;
      int32_t x195 = x188;
      int32_t x196 = x195 + 1;
      int32_t x197 = x196 & 255;
      x188 = x197;
    }
    int32_t x201 = x188;
    int32_t x202 = x4[x201];
    int32_t x204 = x202;
    //#hash_lookup
    int32_t x205 = x21[x204];
    int32_t x206 = x204 * 256;
    int32_t x207 = x206 + x205;
    int32_t x167 = x166 - x158;
    char* x169 = x125+x158;
    for(int x209=x206; x209 < x207; x209++) {
      int32_t x210 = x20[x209];
      char* x211 = x10[x210];
      int32_t x212 = x11[x210];
      char* x213 = x12[x210];
      int32_t x214 = x13[x210];
      char* x215 = x14[x210];
      int32_t x216 = x15[x210];
      char* x217 = x16[x210];
      int32_t x218 = x17[x210];
      bool x220 = x167 == x212;
      bool x235;
      if (x220) {
        int32_t x221 = 0;
        for (;;) {
          int32_t x222 = x221;
          bool x223 = x222 < x167;
          char x224 = x169[x222];
          char x225 = x211[x222];
          bool x226 = x224 == x225;
          bool x227 = x223 && x226;
          if (!x227) break;
          x221 += 1;
        }
        int32_t x232 = x221;
        bool x233 = x232 == x167;
        x235 = x233;
      } else {
        x235 = false;
      }
      if (x235) {
        int32_t x236 = printll(x211);
        printf(",");
        int32_t x238 = printll(x213);
        printf(",");
        int32_t x240 = printll(x215);
        printf(",");
        int32_t x242 = printll(x217);
        printf(",");
        int32_t x244 = printll(x169);
        printf(",");
        printf("%d",x187);
        printf("%s\n","");
      } else {
      }
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
