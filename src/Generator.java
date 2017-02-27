import java.util.*;

/**
 * Created by kai-w on 27/02/17.
 */
public class Generator {
    String seed;
    int offset;

    private ArrayList<String> passwordAL = new ArrayList<>();
    private String password;

    private boolean seedError = false;
    private boolean offsetError = false;

    private String[] vowels = new String[]{"A","E","I","O","U"};
    private HashMap<String, Integer> vowelValues = new HashMap<>();
    private String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0"};
    private HashMap<String, Integer> alphabetValues = new HashMap<>();
    private String[] symbols = new String[]{"!","£","$","^","&","*","@","~","?","#"};
    private HashMap<String, Integer> symbolValues = new HashMap<>();


    public Generator(String seed, String offset) {
        if (seed != null) {
            this.seed = seed.toLowerCase();
        } else {
            seedError = true;
        }

        try {
            this.offset = Integer.parseInt(offset);
        } catch (NumberFormatException e) {
            offsetError = true;
        }
    }

    protected String create() {
        checkErrors();
        initValues();

        vowelShift();
        consonentOrder();

        vowelsToNumbers();
        numberShift();

        incrementLetters();
        addSymbols();

        padToLength();

        stringifyPassword();

        return this.password;
    }

    private void checkErrors() {
        if (seedError) {
            System.out.println("Please enter a valid seed.");
        }
        if (offsetError) {
            System.out.println("Please enter a valid offset.");
        }

        if (seedError || offsetError) {
            System.exit(99);
        }
    }

    private void initValues() {
        passwordAL.trimToSize();
        // Seed to array
        for (int i=0; i<seed.length(); i++) {
            passwordAL.add(Character.toString(seed.charAt(i)));
        }

        // HashMap: vowel -> value
        for (int i=0; i<vowels.length; i++) {
            vowelValues.put(vowels[i].toLowerCase(), i+1+(offset%vowels.length));
        }
        vowels = null;

        // HashMap: letter -> value
        for (int i=0; i<alphabet.length; i++) {
            alphabetValues.put(alphabet[i].toLowerCase(), i+1+(offset%alphabet.length));
        }
        alphabet = null;

        // HashMap: symbol -> value
        for (int i=0; i<symbols.length; i++) {
            symbolValues.put(symbols[i].toLowerCase(), i+1+(offset%symbols.length));
        }
        symbols = null;
    }

    private void vowelShift() {
        passwordAL.trimToSize();

        ArrayList<String> tempVowels = new ArrayList<>();
        int numVowels = 0;

        // Moving vowels to front of ArrayList
        for (int i=0; i<passwordAL.size(); i++) {
            if (vowelValues.containsKey(passwordAL.get(i))) {
                String tempChar = passwordAL.get(i);
                passwordAL.remove(i);
                tempVowels.add(tempChar);
                passwordAL.add(numVowels, tempChar);
                numVowels++;
            }
        }

        // Removing vowels
        for (int i=0; i<numVowels; i++) {
            passwordAL.remove(0);
        }

        // Sorting vowels into alphabetical order
        char[] tempTempVowels = new char[tempVowels.size()];
        for (int i=0; i<tempVowels.size(); i++) {
            tempTempVowels[i] = tempVowels.get(i).charAt(0);
        }
        Arrays.sort(tempTempVowels);

        // Adding sorted vowels back into ArrayList
        for (int i=tempTempVowels.length-1; i>=0; i--) {
            passwordAL.add(0, Character.toString(tempTempVowels[i]));
        }
    }

    private void consonentOrder() {
        passwordAL.trimToSize();
        // Find start of consonents
        int j = 0;
        while (vowelValues.containsKey(passwordAL.get(j))) {
            j++;
        }

        // Move consonents to seperate ArrayList
        ArrayList<String> tempCons = new ArrayList<>();
        int tempSize = passwordAL.size();
        for (int i=j; i<tempSize; i++) {
            if (!vowelValues.containsKey(passwordAL.get(j))) {
                tempCons.add(passwordAL.get(j));
                passwordAL.remove(j);
            }
        }

        // ArrayList to char[] and sort
        char[] tempTempCons = new char[tempCons.size()];
        for (int i=0; i<tempCons.size(); i++) {
            tempTempCons[i] = tempCons.get(i).charAt(0);
        }
        Arrays.sort(tempTempCons);

        // Added sorted consonents back into password
        for (int i=0; i<tempTempCons.length; i++) {
            passwordAL.add(Character.toString(tempTempCons[i]));
        }

    }

    private void vowelsToNumbers() {
        passwordAL.trimToSize();
        for (int i=0; i<passwordAL.size(); i++) {
            if (vowelValues.containsKey(passwordAL.get(i))) {
                passwordAL.set(i, Integer.toString(vowelValues.get(passwordAL.get(i))));
            }
        }
    }

    private void numberShift() {
        passwordAL.trimToSize();
        // Numbers into ArrayList
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i=0; i<passwordAL.size(); i++) {
            try {
                nums.add(Integer.parseInt(passwordAL.get(i)));
            } catch (NumberFormatException e) {

            }
        }

        // Increment numbers
        int first = nums.get(0);
        for (int i=0; i<nums.size(); i++) {
            if (i == nums.size()-1) {
                nums.set(i, nums.get(i)+first);
            } else {
                nums.set(i, nums.get(i)+nums.get(i+1));
            }
        }

        // Add numbers back into password ArrayList
        for (int i=0; i<nums.size(); i++) {
            passwordAL.set(i, Integer.toString(nums.get(i)));
        }
    }

    private void incrementLetters() {
        passwordAL.trimToSize();
        // Numbers to ArrayList
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i=0; i<passwordAL.size(); i++) {
            try {
                nums.add(Integer.parseInt(passwordAL.get(i)));
            } catch (NumberFormatException e) {

            }
        }

        Set es = alphabetValues.entrySet();

        for (int i=nums.size(); i<passwordAL.size(); i++) {
            int numIndex = (i  - nums.size()) % nums.size();
            int letterVal = alphabetValues.get(passwordAL.get(i));

            int newLetterVal = (letterVal + nums.get(numIndex)) % alphabetValues.size();

            Iterator iter = es.iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next();
                int val = Integer.valueOf(entry.getValue().toString());
                String key = entry.getKey().toString();

                if (newLetterVal == val) {
                    passwordAL.set(i, key);
                }
            }
        }
    }

    private void addSymbols() {
        passwordAL.trimToSize();
        // Numbers to ArrayList
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i=0; i<passwordAL.size(); i++) {
            try {
                nums.add(Integer.parseInt(passwordAL.get(i)));
            } catch (NumberFormatException e) {

            }
        }

        String first = "";
        String last = "";

        Set es = symbolValues.entrySet();
        Iterator iter = es.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            int val = Integer.valueOf(entry.getValue().toString());
            String key = entry.getKey().toString();

            if (nums.get(0) == val) {
                first = key;
            }
            if (nums.get(nums.size()-1) == val) {
                last = key;
            }
        }

        passwordAL.add(0, first);
        passwordAL.add(last);
    }

    private void padToLength() {
        passwordAL.trimToSize();
        if (passwordAL.size() < 16) {
            // Numbers to ArrayList
            ArrayList<Integer> nums = new ArrayList<>();
            for (int i=0; i<passwordAL.size(); i++) {
                try {
                    nums.add(Integer.parseInt(passwordAL.get(i)));
                } catch (NumberFormatException e) {

                }
            }

            int padding = nums.size();
            int numToAdd = 16 - passwordAL.size();

            Set es = alphabetValues.entrySet();

            for (int i=0; i<numToAdd; i++) {
                Iterator iter = es.iterator();

                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry)iter.next();
                    int val = Integer.valueOf(entry.getValue().toString());
                    String key = entry.getKey().toString();

                    if (val == padding+i) {
                        passwordAL.add(padding+1, key);
                    }
                }
            }

        }
    }



    private void stringifyPassword() {
        password = String.join("", passwordAL).replaceAll("\\s+","");
    }
}
