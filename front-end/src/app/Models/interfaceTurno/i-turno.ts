import { LocalTime } from "@js-joda/core";

export interface ITurno {
  id:number;
  giornoLezione: string;
  inizioLezione: LocalTime|null;
  fineLezione: LocalTime|null;
}
