
package utils;

public class SuggestionUtil {

    public static String getSuggestions(String region, double carbonEmission, String healthCondition, int age) {
        StringBuilder suggestions = new StringBuilder();

        // Suggestion based on region
        switch (region.toLowerCase()) {
            case "concert":
                suggestions.append("Concerts can be energetic and crowded. If you're attending, consider staying near ventilation or quieter spots.\n");
                break;
            case "kitchen":
                suggestions.append("Kitchens heat up quickly — ensure exhaust fans are running to keep the air fresh.\n");
                break;
            case "garden":
                suggestions.append("A walk in the garden is refreshing. Great choice for fresh air and low emissions.\n");
                break;
            case "industry":
                suggestions.append("Industrial zones often have higher emissions. Stay mindful of your exposure duration.\n");
                break;
            case "home":
                suggestions.append("Home is your comfort zone — optimize fans or AC for energy efficiency.\n");
                break;
            case "office":
                suggestions.append("Office environments can be enclosed — stretch breaks near windows are beneficial.\n");
                break;
            case "roadside":
                suggestions.append("Roadside areas may carry traffic pollutants — try to limit long stops here.\n");
                break;
            case "market":
                suggestions.append("Markets are bustling. Mornings might be a bit cleaner if you’re health conscious.\n");
                break;
            case "classroom":
                suggestions.append("Classrooms can get stuffy — sitting near windows or fans helps stay fresh.\n");
                break;
            case "cafeteria":
                suggestions.append("Cafeterias have mixed air from kitchens and people — pick spots with ventilation.\n");
                break;
            case "pub":
                suggestions.append("Pubs may have smoke or crowd — choosing times with fewer people helps.\n");
                break;
            case "conferencehall":
                suggestions.append("Conference halls are usually closed — a water bottle and regular breaks are great companions.\n");
                break;
            case "mall":
                suggestions.append("Malls have centralized air systems — consider shorter visits if you’re sensitive to indoor air.\n");
                break;
            case "restaurant":
                suggestions.append("Restaurants are relaxing but often crowded. Early reservations can help avoid the rush.\n");
                break;
        }

        // Suggestion based on health condition
        if (healthCondition != null && !healthCondition.isEmpty()) {
            switch (healthCondition.toLowerCase()) {
                case "asthma":
                    suggestions.append("As someone with asthma, consider carrying your inhaler and choosing well-ventilated places.\n");
                    break;
                case "heart patient":
                    suggestions.append(" For heart health, take regular breaks, especially in warm or crowded areas.\n");
                    break;
                case "allergy":
                    suggestions.append(" Allergies can flare up — wearing a mask and avoiding strong scents can really help.\n");
                    break;
                case "none":
                    suggestions.append(" You’re in good health! Still, small habits like drinking water and walking make a difference.\n");
                    break;
                case "diabetes":
                    suggestions.append("Managing diabetes? Stay hydrated and avoid long exposure to heat or crowded areas.\n");
                    break;

                case "hypertension":
                    suggestions.append("With high blood pressure, it's wise to avoid noisy or highly stressful environments. Calm, quiet areas are better.\n");
                    break;

                case "copd":
                    suggestions.append("Living with COPD? Avoid polluted or dusty places, and always carry your medication.\n");
                    break;

                case "anxiety":
                    suggestions.append("If you're prone to anxiety, choose calm environments and avoid overwhelming crowds or noise.\n");
                    break;

                case "arthritis":
                    suggestions.append("For arthritis, avoid staying in cold, damp places for long. Stretching and staying warm helps.\n");
                    break;

                case "migraine":
                    suggestions.append("If you suffer from migraines, avoid bright lights, strong smells, and loud environments.\n");
                    break;

                case "pregnant":
                    suggestions.append("If you’re pregnant, rest frequently, avoid overly crowded or hot areas, and stay hydrated.\n");
                    break;
                default:
                    suggestions.append("Stay mindful of your health condition — consider personalized precautions like staying cool or hydrated.\n");
                    break;

            }
        }

        // Suggestion based on age
        if (age >= 60) {
            suggestions.append(" At your wise age, comfort matters — sit often and hydrate well in busy environments.\n");
        } else if (age >= 30) {
            suggestions.append(" Balancing energy with mindfulness is key — your choices influence long-term wellness.\n");
        } else {
            suggestions.append(" You’re full of energy — still, keeping track of your environment is a smart move.\n");
        }

     // Suggestion based on carbon emission
        if (carbonEmission > 50) {
            suggestions.append("Extreme carbon emission! Immediate action is vital — shift to renewable sources, reduce all non-essential electric usage, and avoid personal vehicle use.\n");
        } else if (carbonEmission > 30) {
            suggestions.append("Very high emissions. Limit air conditioning, consider ride-sharing, and switch to energy-star appliances.\n");
        } else if (carbonEmission > 20) {
            suggestions.append(" Your carbon output is high. Try using natural ventilation, avoid plastic, and reduce screen time.\n");
        } else if (carbonEmission > 10) {
            suggestions.append(" Emission is still high. Swap out high-energy devices, shorten shower times, and avoid disposable items.\n");
        } else if (carbonEmission > 7) {
            suggestions.append("Slightly high — walk instead of driving short distances and unplug chargers when not in use.\n");
        } else if (carbonEmission > 6) {
            suggestions.append(" Getting better! Try composting and using public transport more often.\n");
        } else if (carbonEmission > 5) {
            suggestions.append(" Decent footprint. Keep lights off when not needed and reuse materials when possible.\n");
        } else if (carbonEmission > 4) {
            suggestions.append(" Great! Consider helping others adopt eco habits. Maybe plant a tree or support green communities.\n");
        } else if (carbonEmission > 0.1) {
            suggestions.append("Impressive! You’re living quite sustainably — maintain this with a zero-waste mindset.\n");
        } else {
            suggestions.append(" Outstanding! You're a low-carbon hero. Keep influencing others with your mindful living.\n");
        }


        return suggestions.toString();
    }
}