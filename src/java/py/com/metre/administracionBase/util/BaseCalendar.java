/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.metre.administracionBase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class BaseCalendar {


  private static ThreadLocal<GregorianCalendar> threadLocalCalendar = new ThreadLocal<GregorianCalendar>();

  /**
   * returns the thread-local static calendar
   *   As Calendar was not designed to be thread-safe, we create a new instance
   *   of Calendar for each diferent thread that calls this Helper. IF YOU DO NOT
   *   explicitly make a reference of the calendar instance of this Helper, the
   *   created instance is elegible for garbage collection as the thread finish
   */
  public static Calendar getCalendar()
    {
    if (threadLocalCalendar.get() == null)
      {
      TimeZone tz = TimeZone.getTimeZone("America/Asuncion");
      //TimeZone tz =TimeZone.getDefault();
      TimeZone.setDefault(tz);
      threadLocalCalendar.set(new GregorianCalendar(tz));
      }
    return threadLocalCalendar.get();
    }

  public static void setCalendar(TimeZone tz)
    {
    threadLocalCalendar.set(new GregorianCalendar(tz));
    }


  /**
   * just now
   * @return
   */
  public static Date getAhora()
    {
    getCalendar().setTimeInMillis(System.currentTimeMillis());
    return new Date(getCalendar().getTimeInMillis());
    //return new Date(System.currentTimeMillis());
    }

  public static Date removerTiempoInfo(Date datetime)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(datetime);
    calendar.set(Calendar.HOUR_OF_DAY,0);
    calendar.set(Calendar.MINUTE,0);
    calendar.set(Calendar.SECOND,0);
    calendar.set(Calendar.MILLISECOND,0);
    return calendar.getTime();
    }
  /**
   * simply today, rounded down
   * @return
   */
  public static Date getHoy()
    {
    return removerTiempoInfo(getAhora());
    }
  /**
   * simply tomorow, rounded down
   * in PY this means infinite
   * @return
   */
  public static Date getManhana()
    {
    return getSiguienteDia(getHoy());
    }

  /**
   * get the next day from the current given date
   * @param currentday
   * @return
   */
  public static Date getSiguienteDia(Date currentday)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(currentday);
    calendar.add(Calendar.DATE, 1);
    return calendar.getTime();
    }

  /**
   * get the next month from the current given date
   * @param currentday
   * @return
   */
  public static Date getProximoMes(Date currentday)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(currentday);
    calendar.add(Calendar.MONTH, 1);
    return calendar.getTime();
    }

  public static Date getUltimoDiaDelMes(Date currentday)
  {
  Date proximoMes = getProximoMes(currentday);
  Date ultimoDiaDelMes = getDiaPrevio(proximoMes);
  return ultimoDiaDelMes;
  }

  public static Date getFirstDayOfMonth(Date currentday)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(currentday);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
    }

  /**
   * get the next year from the current given date
   * @param curretndate
   * @return
   */
  public static Date getProximoAnho(Date curretndate)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(curretndate);
    calendar.add(Calendar.MONTH, 12);
    return calendar.getTime();
    }

  /**
   * get the previous day from the current given date
   * @param currentday
   * @return
   */
  public static Date getDiaPrevio(Date currentday)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(currentday);
    calendar.add(Calendar.DATE, -1);
    return calendar.getTime();
    }

  /**
   * get the previous month from the current given date
   * @param currentday
   * @return
   */
  public static Date getMesPrevio(Date currentday)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(currentday);
    calendar.add(Calendar.MONTH, -1);
    return calendar.getTime();
    }

  /**
   * simply yesterday, rounded down
   * @return
   */
  public static Date getAyer()
    {
    return getDiaPrevio(getHoy());
    }

  public static Date getPrimerDiaDelAnho(Date date)
    {
    Calendar currentdate = new GregorianCalendar();
    currentdate.setTime(date);

    Calendar calendar=new GregorianCalendar();
    calendar.set(currentdate.get(Calendar.YEAR), Calendar.JANUARY, 1);
    return calendar.getTime();
    }

  public static Date getUltimoDiaDelAnho(Date date)
    {
    Calendar currentdate = new GregorianCalendar();
    currentdate.setTime(date);

    Calendar calendar=new GregorianCalendar();
    calendar.set(currentdate.get(Calendar.YEAR), Calendar.DECEMBER, 31);
    return calendar.getTime();
    }

  public static long getNrDeDias(Date fromincl, Date till)
    {
    long nro = 0;
    long millisPerDay = 86400000L;
    nro = (till.getTime() - fromincl.getTime()) / millisPerDay;
    if (nro > 0)
      {
      return((till.getTime() - fromincl.getTime()) / millisPerDay);//plus one as the days are inclussive.
      }
    return 0; // Way we return negative numbers ????
    }

  public static long getNrDeDiassInclyNegativo(Date fromincl, Date till)
    {
    long nro = 0;
    long millisPerDay = 86400000L;
    nro = (till.getTime() - fromincl.getTime()) / millisPerDay;

    return((till.getTime() - fromincl.getTime()) / millisPerDay)+1;//plus one as the days are inclussive.
    }



  public static long getNrDeDiasIncl(Date fromincl, Date tillincl)
    {
    return getNrDeDias(fromincl,tillincl)+1;//plus one as the days are inclussive.
    }

  public static long getNrDeMesesIncl(Date fromincl, Date tillincl)
    {
    long days=getNrDeDiasIncl(fromincl,tillincl);
    //find out the amount of months
    double monthdayspart=31;//better approximation than 365.25/12.0;
    if(days>31)
      {//one month of 31 days will be seen as one month, any other longer period will be 2 months or more
      monthdayspart=30.5;
      }
    int months=new Double(Math.ceil(days/monthdayspart)).intValue();//round up, this is the amount of months
    //final test, to adjust wit one month or more if needed
    Calendar fromcalendar = getCalendar();
    Calendar toinclcalendar = getCalendar();
    fromcalendar.setTime(fromincl);
    fromcalendar.add(Calendar.MONTH, months);
    while(fromcalendar.before(toinclcalendar))
      {
      months++;
      fromcalendar.add(Calendar.MONTH, 1);
      }
    return months;
    }



  public static int getNrdeAnhos(Date fromincl, Date till)
    {
    Calendar fromcalendar = Calendar.getInstance();
    fromcalendar.setTime(fromincl);
    Calendar tillcalendar = Calendar.getInstance();
    tillcalendar.setTime(till);
    int fullyears=tillcalendar.get(Calendar.YEAR) - fromcalendar.get(Calendar.YEAR) -1;
    if(tillcalendar.get(Calendar.DAY_OF_YEAR)<fromcalendar.get(Calendar.DAY_OF_YEAR))
      {
      fullyears++;
      }
    return fullyears;
    }

  /**
   *Adds a month to the date, 31/01/05 + 1 month = 28/02/05
   *@return
   */
  public static Date agregarMesesAfecha(Date date, int months)
    {
    Calendar frominclcal=new GregorianCalendar();
    frominclcal.setTime(date);
    frominclcal.add(Calendar.MONTH,months);
    return frominclcal.getTime();
    }

  public static Date agregarAnhosAfecha(Date date, int years)
    {
    Calendar frominclcal=new GregorianCalendar();
    frominclcal.setTime(date);
    frominclcal.add(Calendar.YEAR,years);
    return frominclcal.getTime();
    }

  /**
   * Adds or substract days to the date
   */
  public static Date agregarDiasAfecha(Date date, int days)
    {
    Calendar frominclcal=new GregorianCalendar();
    frominclcal.setTime(date);
    frominclcal.add(Calendar.DAY_OF_MONTH,days);
    return frominclcal.getTime();
    }


  public static boolean comparar(Date date1, Date date2)
    {
    Date strippeddate1 = removerTiempoInfo(date1);
    Date strippeddate2 = removerTiempoInfo(date2);
    if (strippeddate1.equals(strippeddate2))
      {
      return true;
      }
    return false;
    }

  public static Integer getDia(Date date)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_MONTH);
    }

  public static Integer getMes(Date date)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(date);
    return calendar.get(Calendar.MONTH);
    }

  public static Integer getAnho(Date date)
    {
    Calendar calendar=getCalendar();
    calendar.setTime(date);
    return calendar.get(Calendar.YEAR);
    }

  public static boolean estaEntre(Date compareAgainstDate, Date start, Date end)
    {
    if (compareAgainstDate != null && start != null && end != null)
      {
      if (start.before(end))
        {
        if (compareAgainstDate.getTime() >= start.getTime() && compareAgainstDate.getTime() < end.getTime())
          {
          return true;
          }
        }
      }
    return false;
    }

  public static boolean estaEntreIncl(Date compareAgainstDate, Date start, Date end)
    {
    if (compareAgainstDate != null && start != null && end != null)
      {
      if (start.before(end))
        {
        if (compareAgainstDate.getTime() >= start.getTime() && compareAgainstDate.getTime() <= end.getTime())
          {
          return true;
          }
        }
      }
    return false;
    }


  /**
   * returns the elapse time in a string
   *
   * @param start
   * @param end
   * @return String elapseTime
   */
  public static String getTiempoTranscurrido(Date start, Date end)
    {
    long elapsedseconds =(end.getTime() - start.getTime())/1000;
    return elapsedseconds/60+" Minutos "+elapsedseconds%60+" Segundos";
    }

  public static String formatoFechaString(Date fecha)
    {
    SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
    return formatoDeFecha.format(fecha);
    }

  public static Date formatoFechaFecha(Date paramfecha)
    {
     String strFecha = formatoFechaString(paramfecha);
     //DateFormat.getInstance().format(paramfecha);
     SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
     Date fecha = null;
     try {
          fecha = formatoDelTexto.parse(strFecha);
         } catch (ParseException ex) {
         ex.printStackTrace();
         }
     return fecha;
    }

  }
