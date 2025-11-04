package DND.DND.Character_Notes;

import DND.DND.Characters.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Characters_Notes_Service
{
    @Autowired
    private Characters_Notes_Repository characters_notes_repository;

    public Characters_Notes saveStoryAndInventory(Character character, String story, String inventory)
    {
        Characters_Notes characters_notes = new Characters_Notes();
        characters_notes.setName(character.getName());
        characters_notes.setEmail(character.getUser());
        characters_notes.setStory(story);
        characters_notes.setInventory(inventory);
        characters_notes.setNotes("");
        return characters_notes_repository.save(characters_notes);
    }

    public Characters_Notes updateStory(Character character, String new_story)
    {
        Characters_Notes characters_notes = characters_notes_repository.findCharacters_NotesByNameAndEmail(character.getName(), character.getUser());
        if (new_story.equals(""))
        {
            new_story = "";
        }
        characters_notes.setStory(new_story);
        return characters_notes_repository.save(characters_notes);
    }

    public Characters_Notes saveInventory(Character character, String inventory)
    {
        Characters_Notes characters_notes = characters_notes_repository.findCharacters_NotesByNameAndEmail(character.getName(), character.getUser());
        if (inventory.equals(""))
        {
            inventory = "";
        }
        characters_notes.setInventory(inventory);
        return characters_notes_repository.save(characters_notes);
    }

    public Characters_Notes saveNote(Character character, String note)
    {
        Characters_Notes characters_notes = characters_notes_repository.findCharacters_NotesByNameAndEmail(character.getName(), character.getUser());
        if (note.equals(""))
        {
            note = "";
        }
        characters_notes.setNotes(note);
        return characters_notes_repository.save(characters_notes);
    }

    public Characters_Notes getCharacters_Notes(String name, String email)
    {
        return characters_notes_repository.findCharacters_NotesByNameAndEmail(name, email);
    }
}
