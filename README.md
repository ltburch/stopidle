#### StopIdle
On occasion it is necassary to keep a machine from entering idle state to keep an application up or preventing screen lock/disconnection.

To complicate matters more sometimes there are admin controls in place to avoid fixing this with tinkering with settings
Fortunately all is not lost, this program is a simple way to keep a machine or remove settion from entering idle.

Whst it does is flip a keyboard toggle back and forth to prevent the machine from entering idle and it does so without the
use of scripts which can often also be blocked and instead uses Java which is often already a whitelisted program.


```
usage: StopIdle [-h] [-q] [-v] [-t {scroll,caps,number}] [-p PERIOD]

Stop computer from going "idle" to prevent things such as screen lock.

named arguments:
  -h, --help             show this help message and exit
  -q, --quiet            Do not print startup banner
                         (default: false)
  -v, --verbose          Print  messages  when   toggling  keyboard  toggle
                         (default: false)
  -t {scroll,caps,number}, --type {scroll,caps,number}
                         Specify which keyboard toggle  to toggle (default:
                         scroll)
  -p PERIOD, --period PERIOD
                         time in seconds between toggle (default: 60)
```