// ** I18N

// Calendar EN language
// Author: Mihai Bazon, <mishoo@infoiasi.ro>
// Encoding: any
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.

// full day names
Calendar._DN = new Array
("Minggu",
 "Senin",
 "Selasa",
 "Rabu",
 "Kamis",
 "Jumat",
 "Sabtu",
 "Minggu");

// Please note that the following array of short day names (and the same goes
// for short month names, _SMN) isn't absolutely necessary.  We give it here
// for exemplification on how one can customize the short day names, but if
// they are simply the first N letters of the full name you can simply say:
//
//   Calendar._SDN_len = N; // short day name length
//   Calendar._SMN_len = N; // short month name length
//
// If N = 3 then this is not needed either since we assume a value of 3 if not
// present, to be compatible with translation files that were written before
// this feature.

// short day names
Calendar._SDN = new Array
("Min",
 "Sen",
 "Sel",
 "Rab",
 "Kam",
 "Jum",
 "Sab",
 "Min");

// full month names
Calendar._MN = new Array
("Januari",
 "Februari",
 "Maret",
 "April",
 "Mei",
 "Juni",
 "Juli",
 "Agustus",
 "September",
 "Oktober",
 "November",
 "Desember");

// short month names
Calendar._SMN = new Array
("Jan",
 "Feb",
 "Mar",
 "Apr",
 "Mei",
 "Jun",
 "Jul",
 "Agu",
 "Sep",
 "Okt",
 "Nov",
 "Des");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "Tentang Kalender";

Calendar._TT["ABOUT"] =
"Klik pada tanggal yang Anda inginkan untuk memasukkan tanggal tersebut ke form tanggal.";

Calendar._TT["PREV_YEAR"] = "Tahun lalu";
Calendar._TT["PREV_MONTH"] = "Bulan lalu";
Calendar._TT["GO_TODAY"] = "Hari ini";
Calendar._TT["NEXT_MONTH"] = "Bulan depan";
Calendar._TT["NEXT_YEAR"] = "Tahun depan";
Calendar._TT["SEL_DATE"] = "Pilih tanggal";
Calendar._TT["DRAG_TO_MOVE"] = "Drag untuk memindahkan";
Calendar._TT["PART_TODAY"] = " (hari ini)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "Pindah %s di awal";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "Tutup";
Calendar._TT["TODAY"] = "Hari ini";
Calendar._TT["TIME_PART"] = "(Shift-)Click or drag to change value";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Calendar._TT["TT_DATE_FORMAT"] = "%A, %e %b";

Calendar._TT["WK"] = "wk";
Calendar._TT["TIME"] = "Time:";
